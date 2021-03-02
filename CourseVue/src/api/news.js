import { pureGet } from "../common/ajax";

export const get = () => pureGet("/sdnu/news");

export const getNew = () => pureGet("/sdnu/news/new");
