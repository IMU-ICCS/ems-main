<template>
    <div class="input-group input-group-sm">
        <select :id="id" :data-row="row" :value="initValue" class="form-control form-control-sm">
            <option v-for="dest in destinations" :key="dest.value" :value="dest.value">{{dest.text}}</option>
        </select>
    </div>
</template>

<script>
export default {
    name: 'Topics List widget',
    props: {
        id: String,
        row: Number,
        selected: String,
        topics: Object,
        destinations: Array,
    },
    data() {
        return {
            initValue: this.selected,
            currValue: '',
        };
    },
    beforeUpdate() {
        this.currValue = this.$el.firstChild.value;
    },
    updated() {
        this.$el.firstChild.value = this.currValue;
        if (this.$el.firstChild.value==='') this.$el.firstChild.value = this.selected;
    },
}
</script>