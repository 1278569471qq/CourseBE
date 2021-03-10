<template>
    <div>请稍等一下。。。。。。。</div>
</template>

<script>
    import * as api from "../api/user";

    export default {
        name: "authLogin",
        methods : {

        },
        async mounted() {
            const href = window.location.href;
            const index = href.indexOf("=");
            if (index > 0){
                const lastIndexOf = href.indexOf("&");
                const token = href.substring(index + 1, lastIndexOf);
                await api.tokenApi(token).then(res => {
                    this.$message.success(res == null ? "成功" : res);
                });
            }
            setTimeout(() => {
                self.opener.location.reload();
                window.close();
            }, 2000);
        }
    }
</script>

<style scoped>

</style>