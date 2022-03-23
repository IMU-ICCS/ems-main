<template>
    <textarea :id="id" :placeholder="placeholder" v-model="currentValue"
              ondragover="event.preventDefault()" @drop="onDrop"
    />
</template>

<script>
export default {
    name: 'Textarea with file drop',
    props: {
        id: String,
        value: { type: String, default: '', required: false },
        placeholder: String,
    },
    data() {
        return {
            currentValue: this.getDefaultValue()
        }
    },
    methods: {
        getDefaultValue() {
            let children = this.$slots.default && this.$slots.default();
            if (children && children.length>0) {
                let s = '';
                for (let i=0; i<children.length; i++) {
                    s += children[i].children;
                    s += ' ';
                }
                return s;
            }
            return this.value;
        },
        onDrop(e) {
            let target = e.target;

            let files = e.dataTransfer.files;
            if (files.length > 0) {
                let file = files[0];
                e.preventDefault();

                let reader = new FileReader();
                reader.onload = function(event) {
                    if (target.value.trim()==='' || confirm('Do you want to replace current content?'))
                        target.value = event.target.result;
                };
                reader.readAsText(file);

                return false;
            }
        }
    }
}
</script>