import { useEffect, useRef, useState } from "react";

import QuestionForm from "../components/QuestionForm";
import QuestionList from "../components/QuestionList";
import AnalysisPanel from "../components/AnalysisPanel";
import "../App.css";
import { listQuestions } from "../services/questionService";
import Dashboard from "../components/Dashboard";


function Home() {

    const [questions, setQuestions] = useState([]);

    const [analysis, setAnalysis] = useState("");

    const [questionToEdit, setQuestionToEdit] = useState(null);

    const analysisRef = useRef(null);


    useEffect(() => {

        loadQuestions();

    }, []);


    useEffect(() => {

        if (!analysis) return;

        setTimeout(() => {

            analysisRef.current?.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });

        }, 100);

    }, [analysis]);

    async function loadQuestions() {

        const data = await listQuestions();

        setQuestions(data);

    }


    return (

        <div className="app">

            <h1 className="title">
                📚 Study Assistant
            </h1>

            <Dashboard questions={questions} />

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


            <div
                ref={analysisRef}
                className="card analysis"
            >

                <AnalysisPanel
                    analysis={analysis}
                />

            </div>


        </div>

    );

}

export default Home;