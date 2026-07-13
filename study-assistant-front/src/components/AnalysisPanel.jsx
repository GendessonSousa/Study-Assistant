import ReactMarkdown from "react-markdown";

function AnalysisPanel({ analysis }) {

    return (

        <div>

            <h2>🤖 Análise da IA</h2>

            <div className="analysis-box">

                {
                    analysis
                        ? <ReactMarkdown>{analysis}</ReactMarkdown>
                        : <p>Nenhuma análise gerada.</p>
                }

            </div>
                
        
        </div>

        

    );

}

export default AnalysisPanel;