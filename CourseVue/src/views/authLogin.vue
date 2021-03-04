<template>
    <a href="javascript:window.opener=null;window.open('','_self');window.close();">关闭</a>
</template>

<script>
    import * as api from "../api/user";;
    export default {
        name: "authLogin",
        methods : {

        },
        async mounted() {
            const href = window.location.href;
            const index = href.indexOf("=");
            if (index <= 0) {
                window.close();
                this.$router.push('/');
            } else {
                const lastIndexOf = href.indexOf("&");
                const token = href.substring(index + 1, lastIndexOf);
                await api.tokenApi(token).then(res => {
                    console.log(res);
                });
                this.$router.push('/');
            }
        }
    }
</script>

<style scoped>

</style>