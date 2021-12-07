<template>
    <div class="input-group input-group-sm">
        <select :id="id" :data-row="row" :value="initValue" class="form-control form-control-sm">
            <option v-if="optionAll" value="*">All clients</option>
            <option v-for="c in clients" :key="c.id" :value="c.id">{{c.name}}</option>
            <option v-if="optionServer" value="0">EMS server (only)</option>
        </select>
    </div>
</template>

<script>
export default {
    name: 'Clients List widget',
    props: {
        id: String,
        row: Number,
        selected: String,
        optionAll: { type:Boolean, default:true },
        optionServer: { type:Boolean, default:true },
        clients: Object,
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