<template>
    <header id="header" class="header bg-white">
        <div class="navbar-container">
            <router-link to="/" class="navbar-logo">
                <img src="@/assets/images/logo.png" alt="</>">
            </router-link>
            <div class="navbar-menu">
                <el-popover
                        placement="bottom"
                        width="400"
                        trigger="click"
                        class="categoryPopover">
                    <TagCategoryTemplate type="category" message="👇 Find blog post by categories."
                                         url="api/categories"></TagCategoryTemplate>
                    <el-button slot="reference" class="categories">Categories</el-button>
                </el-popover>
                <el-popover
                        placement="bottom"
                        width="400"
                        trigger="click"
                        class="tagPopover">
                    <TagCategoryTemplate type="tag" message="👇 Find blog post by tags."
                                         url="api/tags"></TagCategoryTemplate>
                    <el-button slot="reference" class="tags">Tags</el-button>
                </el-popover>
                <router-link to="/archives">Archives</router-link>
                <router-link to="/about">About</router-link>
                <el-input
                        class="inline-input search"
                        prefix-icon="el-icon-search"
                        v-model="keyword"
                        placeholder="Search"
                        @keyup.enter.native="search"
                ></el-input>
            </div>
            <div class="navbar-mobile-menu" onclick="">
                <span class="icon-menu cross"><span class="middle"></span></span>
                <ul>
                    <li>
                        <router-link to="/archives">Archives</router-link>
                    </li>
                    <li>
                        <router-link to="/about">About</router-link>
                    </li>
                    <li>
                        <el-input
                                class="inline-input search"
                                prefix-icon="el-icon-search"
                                v-model="keyword"
                                placeholder="Search"
                                @keyup.enter.native="search"
                        ></el-input>
                    </li>
                </ul>
            </div>
        </div>
    </header>
</template>

<script>
    import TagCategoryTemplate from '@/components/TagCategoryTemplate'

    export default {
        name: "Header",
        data() {
            return {
                keyword: '',
                isCollapse: true
            }
        },
        components: {
            TagCategoryTemplate
        },
        methods: {
            search() {
                if (this.keyword != null && this.keyword.length > 0) {
                    this.$router.replace('/search/' + this.keyword).catch(e => {
                        console.log(e);
                    });
                }
            },
        }
    }
</script>

<style scoped>


    .inline-input {
        /*margin-top: 10px;*/
        width: 150px;
        display: inline-block;
    }

    .el-menu-item:hover {
        background-color: #fff;
    }

    .tags, .categories {
        background-color: #fff;
        border: none;
        color: #313131;
        font-weight: normal;
        font-family: "Source Sans Pro", serif;
    }

    .tags:hover, .categories:hover {
        color: #eb5055;
    }

    .el-popover {
        background-color: rgb(246, 246, 246);
    }

    #menu-icon {
        font-size: 20px;
    }

    img {
        padding-bottom: 3px;
    }

    li > .el-input {
        background-color: #EEEEEE;
        padding-right: 15px;
    }
</style>

<style>
    .search input.el-input__inner {
        border-radius: 20px;
    }
</style>