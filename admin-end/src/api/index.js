import request from '../utils/request';
import {http} from '../utils/request';

export const searchBook = title => {
    return http('GET', '/book/search',title,4);
};
//commentget
export const login = user => {
	return http('POST', '/login/adminstrator', user, 1);
}
export const readerDetail = data => {
    return http('GET', '/login/adminDetail',data,4);
};
export const addBook = data => {
    return http('POST', '/book/insert',data,1);
};
export const editBook = data => {
    return http('POST', '/book/update',data,1);
};
export const deleteBook = data => {
    return http('GET', '/book/delete',data,4);
};