import { useEffect, useState } from "react";
import {
    createQuestion,
    updateQuestion
} from "../services/questionService";

function QuestionForm({
    onQuestionCreated,
    questionToEdit,
    onFinishEditing
}) {

    const [statement, setStatement] = useState("");
    const [subject, setSubject] = useState("");

    const [optionA, setOptionA] = useState("");
    const [optionB, setOptionB] = useState("");
    const [optionC, setOptionC] = useState("");
    const [optionD, setOptionD] = useState("");
    const [optionE, setOptionE] = useState("");

    const [correctAnswer, setCorrectAnswer] = useState("A");
    const [userAnswer, setUserAnswer] = useState("A");

    useEffect(() => {

        if (!questionToEdit) return;

        setStatement(questionToEdit.statement);
        setSubject(questionToEdit.subject);

        setCorrectAnswer(questionToEdit.correctAnswer);
        setUserAnswer(questionToEdit.userAnswer);

        setOptionA(questionToEdit.questionOptions[0]?.text || "");
        setOptionB(questionToEdit.questionOptions[1]?.text || "");
        setOptionC(questionToEdit.questionOptions[2]?.text || "");
        setOptionD(questionToEdit.questionOptions[3]?.text || "");
        setOptionE(questionToEdit.questionOptions[4]?.text || "");

    }, [questionToEdit]);

    async function handleSubmit(e) {

        e.preventDefault();

        const question = {

            statement,

            subject,

            correctAnswer,

            userAnswer,

            questionOptions: [

                { letter: "A", text: optionA },
                { letter: "B", text: optionB },
                { letter: "C", text: optionC },
                { letter: "D", text: optionD },
                { letter: "E", text: optionE }

            ]

        };

        try {

            if (questionToEdit) {

                await updateQuestion(
                    questionToEdit.id,
                    question
                );

                alert("Questão atualizada!");

                onFinishEditing();

            } else {

                await createQuestion(question);

                alert("Questão cadastrada!");

                onQuestionCreated();

            }

        } catch (error) {

            console.error(error);

            alert("Erro ao salvar questão.");

        }

    }
    return (

        <form onSubmit={handleSubmit}>

            <h2>
                {questionToEdit ? "Editar Questão" : "Nova Questão"}
            </h2>

            <input
                placeholder="Enunciado"
                value={statement}
                onChange={(e) => setStatement(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Matéria"
                value={subject}
                onChange={(e) => setSubject(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Alternativa A"
                value={optionA}
                onChange={(e) => setOptionA(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Alternativa B"
                value={optionB}
                onChange={(e) => setOptionB(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Alternativa C"
                value={optionC}
                onChange={(e) => setOptionC(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Alternativa D"
                value={optionD}
                onChange={(e) => setOptionD(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Alternativa E"
                value={optionE}
                onChange={(e) => setOptionE(e.target.value)}
            />

            <br /><br />

            <label>Resposta correta</label>

            <select
                value={correctAnswer}
                onChange={(e) => setCorrectAnswer(e.target.value)}
            >
                <option>A</option>
                <option>B</option>
                <option>C</option>
                <option>D</option>
                <option>E</option>
            </select>

            <br /><br />

            <label>Resposta do aluno</label>

            <select
                value={userAnswer}
                onChange={(e) => setUserAnswer(e.target.value)}
            >
                <option>A</option>
                <option>B</option>
                <option>C</option>
                <option>D</option>
                <option>E</option>
            </select>

            <br /><br />

            <button
                type="submit"
                className="save"
            >

                {
                    questionToEdit
                        ? "Atualizar Questão"
                        : "Salvar Questão"
                }

            </button>

        </form>

    );

}

export default QuestionForm;