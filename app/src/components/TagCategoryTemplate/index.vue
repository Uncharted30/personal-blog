<template>
    <div class="search-page">
        <div class="search-tags" v-if="list !== null">
            <p>{{message}}</p>
            <router-link :to="'/' + type + '/' + item.id" v-for="item in list" :key="item.id" class="bg-white">#
                {{item.name}}
            </router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                list: null,
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.$axios.get(this.url).then(response => {
                    if (response.data.code === 200) {
                        this.list = response.data.data;
                    } else {
                        this.$message.error("Failed to get list of " + this.type + ", please refresh the page and try again. " + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error("Failed to get list of " + this.type + ", please refresh the page and try again. " + e);
                })
            }
        },
        props: ['message', 'type', 'url']
    }
</script>

<style scoped>
    .bg-white {
        box-shadow: 1px 1px 5px 0 grey !important;
    }

    .search-tags {
        padding-top: 0 !important;
    }
</style>