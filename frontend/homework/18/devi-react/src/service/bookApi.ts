import type { Book } from '../types/Book';

const BASE_URL = 'http://localhost:3000';

export const fetchAllBooks = async (): Promise<Book[]> => {
const response = await fetch(`${BASE_URL}/books`);

if (!response.ok) {
throw new Error('Failed to fetch all books');
}

return response.json();
};

export const fetchAvailableBooks = async (): Promise<Book[]> => {
    const response = await fetch(`${BASE_URL}/books/available`);

    if (!response.ok) {
    throw new Error('Failed to fetch available books');
    }

    return response.json();
};

export const fetchBooksByYearRange = async (
    startYear: number,
    endYear: number
    ): Promise<Book[]> => {
    const response = await fetch(`${BASE_URL}/books/yearrange?startYear=${startYear}&endYear=${endYear}`);

    if (!response.ok) {
    throw new Error('Failed to fetch books by year range');
    }

    return response.json();
    };

export const searchBooksApi = async (
title?: string,
author?: string
): Promise<Book[]> => {
const params = new URLSearchParams();

if (title) params.append('title', title);
if (author) params.append('author', author);

const response = await fetch(`${BASE_URL}/books/search?${params.toString()}`);

if (!response.ok) {
throw new Error('Failed to search books');
}

const json = await response.json();
return Array.isArray(json) ? json : json.data ?? [];
};