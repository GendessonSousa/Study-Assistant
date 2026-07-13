import axios from "axios";

const API = "http://localhost:8080/question";

export async function listQuestions() {
    const response = await axios.get(`${API}/list`);
    return response.data;
}

export async function createQuestion(question) {
    const response = await axios.post(`${API}/create`, question);
    return response.data;
}

export async function deleteQuestion(id) {
    await axios.delete(`${API}/delete/${id}`);
}

export async function generateAnalysis(id) {
    const response = await axios.post(`${API}/${id}/analysis`);
    return response.data;
}

export async function updateQuestion(id, question){

    const response = await axios.put(
        `${API}/update/${id}`,
        question
    );

    return response.data;

}

export async function regenerateAnalysis(id){

    const response = await axios.post(
        `${API}/${id}/analysis/regenerate`
    );

    return response.data;

}