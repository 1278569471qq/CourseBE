<template>
  <div class="sidebar">
<!--      <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
<!--        <el-radio-button :label="false">展开</el-radio-button>-->
<!--        <el-radio-button :label="true">收起</el-radio-button>-->
<!--      </el-radio-group>-->
    <el-menu  class="el-menu-vertical-demo" :collapse="isCollapse" router  :default-active="routePath">
      <template v-for="item in items">
        <el-menu-item :index="item.index"  :key="item.index" >
          <i :class="item.icon"></i>
          <span slot="title">{{ item.title }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "SideBar",
  data() {
    return {
      isCollapse: false
    };
  },
  computed: {
    items() {
      return this.$store.state.sideBarItems;
    },
    routePath() {
      return this.$route.path;
    }
  }
};
</script>

<style scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 170px;
    min-height: 400px;
  }
/*.sidebar {*/
/*  display: block;*/
/*  position: absolute;*/
/*  left: 0;*/
/*  top: 70px;*/
/*  bottom: 0;*/
/*  overflow-y: scroll;*/
/*}*/

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 200px;
}

.sidebar > ul {
  height: 100%;
}
</style>
