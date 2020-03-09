<template>
    <el-select v-model="categoryId" placeholder="Select a category..." filterable
               clearable :loading="categoryLoading" loading-text="Loading..." id="categories" @change="onChange">
        <el-option :value="c.id" :label="c.name" v-for="c in categories" :key="c.id">
            {{c.name}}
        </el-option>
    </el-select>
</template>

<script>
    export default {
        name: "index",
        created() {
            this.getCategories();
        },
        data() {
            return {
                categoryLoading: true,
                categories: null,
                categoryId: null
            }
        },
        methods: {
            getCategories() {
                this.$axios.get('api/categories').then(response => {
                    if (response.data.code === 200) {
                        this.categories = response.data.data;
                        this.categoryLoading = false;
                    } else {
                        this.$message.error('Filed loading categories. ' + response.data.data.message)
                    }
                }).catch((e) => {
                    this.$message.error('Filed loading categories. ' + e);
                });
            },
            onChange() {
                this.$emit('setCategoryId', this.categoryId);
                this.$emit('onChange')
            }
        }
    }
</script>

<style scoped>

</style>