import axios from "axios";

const API_BASE_URL = "http://localhost:9090/";
export const getStatusSummary = async () => {
    try {
        const url = `${API_BASE_URL}summary/status`;
        const response = await axios.get(url);
        const responseData = response.data;

        return responseData;
    } catch (error) {
        const errorData = {
            status: error.response ? error.response.status : null,
            data: error.response ? error.response.data : null,
            message: error.message,
        };

        return errorData;
    }
};

export const getLocomotiveSummary = async () => {
    try {
        const url = `${API_BASE_URL}summary/locomotives`;
        const response = await axios.get(url);
        const responseData = response.data;

        return responseData;
    } catch (error) {
        const errorData = {
            status: error.response ? error.response.status : null,
            data: error.response ? error.response.data : null,
            message: error.message,
        };

        return errorData;
    }
};