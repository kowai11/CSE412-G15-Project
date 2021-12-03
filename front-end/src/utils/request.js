import axios from 'axios';
import QS from "qs";

const service = axios.create({
    baseURL: 'http://localhost:8080',
    //timeout: 5000
});

service.interceptors.request.use(
	(config) => {
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response.data;
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log(error);
        return Promise.reject(error);
    }
);

export function http(method, url, data, ContentType = 1) {
	const dataType = (method === 'POST') ? 'data' : 'params';
	let type = '';
	let header ={}
	switch (ContentType) {
		case 1:
			type = "application/json";
			break;
		case 2:
			type = "application/x-www-form-urlencoded";
			break;
		case 3:
			type = "multipart/form-data";			break;
		case 4:
			type = "application/x-www-form-urlencoded";
			break;
	}
	if(localStorage.getItem('token')){
		header={
			'Content-Type': type,
			'Authorization': 'JWT '+localStorage.getItem('token')
		}
	}else{
		
		header={
			'Content-Type': type
		}
	}
	if(ContentType===3){
		return service({
			url: url,
			headers: header,
			method: method,
			[dataType]: data
		})
	}
	else if(ContentType===4){
		return service({
			url: url,
			headers: header,
			method: method,
			[dataType]: data
		})
	}
	else {
		return service({
			url: url,
			headers: header,
			method: method,
			[dataType]: ContentType === 1 ? JSON.stringify(data) : QS.stringify(data)
		})
	}
}
