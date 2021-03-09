<template>
    <div>请稍等。。。。。。。</div>
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
                    console.log(res);
                });
            }
            self.opener.location.reload();
            window.close();
        }
    }
</script>

<style scoped>

</style>