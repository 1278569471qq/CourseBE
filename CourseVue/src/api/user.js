import { post, pureGet } from "../common/ajax";
import * as ajax from "../common/ajax";

export const login = (username, password, userType) =>
  post("/user/login", {
    username: username,
    password: password,
    userType: userType
  });

export const getLoginStatus = () => pureGet("/user/login/status");

export const logout = () => pureGet("/user/logout");

export const tokenApi = (auth) =>
    ajax.get("auth", {
        token: auth
    });
export const getAuthorUrl = () => ajax.pureGet("/qq/author/url")


export const getAuthorBind = () => ajax.pureGet("/qq/author/bind")


export const getLogs = (page) => ajax.pureGet("/user/log/" + page)


export const getCurrentUsers = () => ajax.pureGet("/user/current/user")

