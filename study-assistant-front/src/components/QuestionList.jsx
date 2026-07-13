import { useState } from "react";
import {
    deleteQuestion,
    generateAnalysis,
    regenerateAnalysis
} from "../services/questionService";

function QuestionList({
    questions,
    onRefresh,
    onAnalysisGenerated,
    onEdit
}) {

    const [loading, setLoading] = useState({
        id: null,
        action: null
    });

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

            setLoading({
                id,
                action: "analysis"
            });

            const analysis = await generateAnalysis(id);

            onAnalysisGenerated(analysis.analysis);

        } catch (error) {

            console.error(error);

            alert("Erro ao gerar análise.");

        } finally {

            setLoading({
                id: null,
                action: null
            });

        }
    }

    async function handleRegenerateAnalysis(id) {

        try {

            setLoading({
                id,
                action: "regenerate"
            });

            const analysis = await regenerateAnalysis(id);

            onAnalysisGenerated(analysis.analysis);

        } catch (error) {

            console.error(error);

            alert("Erro ao gerar nova análise.");

        } finally {

            setLoading({
                id: null,
                action: null
            });

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
                            question.questionOptions
                                .filter(option => option.text && option.text.trim() !== "")
                                .map(option => (

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
                                disabled={loading.id === question.id}
                                onClick={() => handleAnalysis(question.id)}
                            >
                                {
                                    loading.id === question.id &&
                                        loading.action === "analysis"
                                        ? "Gerando..."
                                        : "🤖 Analisar"
                                }
                            </button>

                            <button
                                className="regenerate"
                                disabled={loading.id === question.id}
                                onClick={() => handleRegenerateAnalysis(question.id)}
                            >
                                {
                                    loading.id === question.id &&
                                        loading.action === "regenerate"
                                        ? "Gerando..."
                                        : "🔄 Nova análise"
                                }
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