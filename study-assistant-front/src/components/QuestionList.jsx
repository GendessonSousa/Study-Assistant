import { useState } from "react";
import { deleteQuestion, generateAnalysis } from "../services/questionService";

function QuestionList({
    questions,
    onRefresh,
    onAnalysisGenerated,
    loading,
    setLoading,
    onEdit
}) {
    const [loadingQuestionId, setLoadingQuestionId] = useState(null);

    async function handleDelete(id) {

        if (!window.confirm("Deseja excluir esta questão?")) {
            return;
        }

        try {

            await deleteQuestion(id);

            onRefresh();

        } catch (error) {

            console.error(error);

            alert("Erro ao excluir.");

        }

    }

    async function handleAnalysis(id) {

        try {

            setLoadingQuestionId(id);

            const analysis = await generateAnalysis(id);

            onAnalysisGenerated(analysis.analysis);

        } catch (error) {

            console.error(error);

            alert("Erro ao gerar análise.");

        } finally {

            setLoadingQuestionId(null);

        }
    }

    return (

        <div>

            <h2>Questões</h2>

            {
                questions.map(question => (

                    <div
                        key={question.id}
                        className="question-card"
                    >

                        <h3>{question.subject}</h3>

                        <p>
                            <strong>Enunciado:</strong><br />
                            {question.statement}
                        </p>

                        <hr />

                        {
                            question.questionOptions.map(option => (

                                <p key={option.letter}>

                                    <strong>{option.letter})</strong> {option.text}

                                </p>

                            ))
                        }

                        <hr />

                        <p>

                            <strong>Resposta do aluno:</strong> {question.userAnswer}

                        </p>

                        <p>

                            <strong>Resposta correta:</strong> {question.correctAnswer}

                        </p>

                        <div className="buttons">

                            <button
                                className="analyze"
                                disabled={loadingQuestionId === question.id}
                                onClick={() => handleAnalysis(question.id)}
                            >
                                {loadingQuestionId === question.id
                                    ? "Gerando..."
                                    : "🤖 Analisar"}
                            </button>

                            <button
                                className="delete"
                                onClick={() => handleDelete(question.id)}
                            >
                                🗑 Excluir
                            </button>

                            <button
                                className="edit"
                                onClick={() => onEdit(question)}
                            >
                                ✏️ Editar
                            </button>

                        </div>

                    </div>

                ))
            }

        </div>

    );

}

export default QuestionList;