import * as ajax from "../../common/ajax";

export const get = id => ajax.get("/admin/admin/" + id);

export const create = entity => ajax.post("/admin/admin", entity);

export const deleteItem = id => ajax.pureDelete("/admin/admin/" + id);

export const update = entity => ajax.put("/admin/admin", entity);

export const list = () => ajax.pureGet("/admin/admin");

export const getLikeData = type => ajax.pureGet("/likeData/" + type);

export const getTeacherLikeData = type => ajax.pureGet("/teacher/likeData/" + type);

export const getStudentLikeData = type => ajax.pureGet("/student/likeData/" + type);
