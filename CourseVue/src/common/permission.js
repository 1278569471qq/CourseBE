let Permission = {};

Permission.no = 0;
Permission.department = 1;
Permission.major = 2;
Permission.class = 4;
Permission.student = 8;
Permission.teacher = 16;
Permission.course = 32;
Permission.studentCourse = 64;
Permission.admin = 128;
Permission.log = 256;

Permission.permissionCount = 9;

export default Permission;
