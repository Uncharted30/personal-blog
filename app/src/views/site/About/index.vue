<template>
  <div id="app-container">

    <article class="main-content page-page">
      <div class="post-header">
        <h1 class="post-title" itemprop="name headline"> About Me </h1>
        <div class="post-data">
          <time v-if="user !== null" :datetime="user.bioEditTime" itemprop="datePublished">Published on {{user.bioEditTime}}</time>
        </div>

      </div>
      <div id="post-content" class="post-data post-content" v-if="user !== null" v-html="user.bio"></div>
      <br>
      <user-card :userInfo="user" :admin="false" class="user-card"></user-card>
    </article>
  </div>

</template>

<script>
  import UserCard from "@/views/admin/Account/component/UserCard";

  export default {
    name: "index",
    data() {
      return {
        user: null
      }
    },
    created() {
      this.getUserInfo();
    },
    methods: {
      getUserInfo() {
        this.$axios.get('api/user').then(response => {
          if (response.data.code === 200) {
            this.user = response.data.data;
          } else {
            this.$message.error("Failed to get content, please refresh the page and try again. " + response.data.message);
          }
        }).catch(e => {
          this.$message.error("Failed to get content, please refresh the page and try again. " + e);
        })
      }
    },
    components: {
      UserCard
    }
  }
</script>
<style lang="scss" scoped>
  @import "~@/assets/styles/style.min.css";

  .page-page {
    padding-top: 80px;
  }

  .user-card {
    width: 100%;
    display: inline-block;
    float: bottom;
  }

  .main-content {
    width: 100%;
    height: 100%;
    background-color: #fff;
  }

  #app-container {
    background-color: #fff;
  }
</style>
