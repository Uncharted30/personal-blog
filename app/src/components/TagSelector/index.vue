<template>
    <el-select v-model="selectedTags" multiple placeholder="Select tags..." filterable id="tags"
               :loading="tagLoading" loading-text="Loading..." @change="changeTags">
        <el-option :value="t.id" :label="t.name" v-for="t in tags" :key="t.id">
            {{t.name}}
        </el-option>
    </el-select>
</template>

<script>
    export default {
        name: "index",
        created() {
          this.getTags()
        },
        data() {
            return {
                selectedTags: [],
                tagLoading: true,
                tags:null
            }
        },
        methods: {
            getTags() {
                this.$axios.get('api/tags').then(response => {
                    if (response.data.code === 200) {
                        this.tags = response.data.data;
                        this.tagLoading = false;
                    } else {
                        this.$message.error('Failed to load tags, please try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to load tags, please try again. ' + e);
                })
            },
            changeTags() {
                this.$emit('onChange')
            },
        }
    }
</script>

<style scoped>

</style>