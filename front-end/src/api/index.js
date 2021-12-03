import request from '../utils/request';
import {http} from '../utils/request';

export const fetchData = query => {
    return http('GET', '/result/',query,4);
};
export const login = user => {
	return http('POST', '/login/reader', user, 1);
}
export const register = user => {
	return http('POST', '/user/register/', user, 1);
}
export const getCaptcha = () => {
	return http('GET', '/user/images/');
}
export const upForcastData = data => {
	return http('POST', '/test/upload/', data, 2);
}
export const upload = data => {
	return http('POST', '/train/upload/', data, 3);
}
export const getdash = () => {
	return http('GET', '/console/');
}

export const getpie = () => {
	return http('GET', '/train/companyInfo/');
}