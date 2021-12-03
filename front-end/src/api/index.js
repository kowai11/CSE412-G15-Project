import request from '../utils/request';
import {http} from '../utils/request';

export const searchBook = title => {
    return http('GET', '/book/search',title,4);
};
//commentget
export const borrowBook = data => {
    return http('GET', '/book/borrow',data,4);
};
export const returnBook = data => {
    return http('GET', '/book/return',data,4);
};
export const login = user => {
	return http('POST', '/login/reader', user, 1);
}
export const readerDetail = data => {
    return http('GET', '/login/readerDetail',data,4);
};
export const getBorrow = data => {
    return http('GET', '/login/getBorrow',data,4);
};