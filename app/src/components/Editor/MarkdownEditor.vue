<template>
    <div class="editor-container">
        <markdown-editor ref="markdownEditor" v-model="md" height="600px"></markdown-editor>
    </div>
</template>

<script>
    import MarkdownEditor from '@/components/MarkdownEditor'

    export default {
        name: 'markdown',
        components: {MarkdownEditor},
        props: ['content'],
        data() {
            return {
                markdownId: this.id,
                md: '',
                editor: {
                    md: '',
                    html: '',
                }
            }
        },
        watch: {
            content(newVal) {
                this.md = newVal; //监听父组件传来的content数据，只会执行一次，用来给子组件赋初始值
            },
            md(newVal) {
                //监听子组件中数据
                this.editor.html = this.$refs.markdownEditor.getHtml();
                this.editor.md = newVal;
                this.$emit('editor', this.editor);
            },
        },
        methods: {
            getLatestHtml() {
                return this.$refs.markdownEditor.getHtml();
            },
            getLatestMd() {
                return this.$refs.markdownEditor.getValue();
            }
        },
    }
</script>

<style scoped>
    .editor-container {
        margin-bottom: 30px;
    }
</style>
