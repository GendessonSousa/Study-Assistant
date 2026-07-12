import { useEffect, useState } from "react";

import QuestionForm from "../components/QuestionForm";
import QuestionList from "../components/QuestionList";
import AnalysisPanel from "../components/AnalysisPanel";
import "../App.css";
import { listQuestions } from "../services/questionService";


function Home() {

    const [questions, setQuestions] = useState([]);

    const [analysis, setAnalysis] = useState("");

    const [questionToEdit, setQuestionToEdit] = useState(null);


    useEffect(() => {

        loadQuestions();

    }, []);


    async function loadQuestions() {

        const data = await listQuestions();

        setQuestions(data);

    }


    return (

        <div className="app">

            <h1 className="title">
                📚 Study Assistant
            </h1>


            <div className="content">

                <div className="card">

                    <QuestionForm
                        onQuestionCreated={loadQuestions}
                        questionToEdit={questionToEdit}
                        onFinishEditing={() => {
                            setQuestionToEdit(null);
                            loadQuestions();
                        }}
                    />

                </div>


                <div className="card">

                    <QuestionList
                        questions={questions}
                        onRefresh={loadQuestions}
                        onAnalysisGenerated={setAnalysis}
                        onEdit={setQuestionToEdit}
                    />

                </div>

            </div>


            <div className="card analysis">

                <AnalysisPanel
                    analysis={analysis}
                />

            </div>


        </div>

    );

}

export default Home;