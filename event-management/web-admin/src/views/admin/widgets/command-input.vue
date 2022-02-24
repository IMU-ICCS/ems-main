<template>
    <div class="input-group input-group-sm">
        <input :id="id" :data-row="row" type="text" :value="initValue" class="form-control"
               placeholder="Write a command" required/>
    </div>
</template>

<script>
export default {
    name: 'Command input widget',
    props: {
        id: String,
        row: Number,
        value: String,
    },
    data() {
        return {
            initValue: this.value,
            currValue: '',
        };
    },
    beforeUpdate() {
        this.currValue = this.$el.firstChild.value;
    },
    updated() {
        this.$el.firstChild.value = this.currValue;
        if (this.$el.firstChild.value==='') this.$el.firstChild.value = this.value;
    },
}
</script>