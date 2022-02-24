<template>
    <div :id="id" v-if="nodeType==='ems'">
        <span :style="style('fnSshConsole')" @click="fnSshConsole" title="SSH console"><i class="fas fa-terminal"/></span>&nbsp;&nbsp;
        <span :style="style('fnSendEventToAllNodes')" @click="fnSendEventToAllNodes" title="Send an event to all nodes"><i class="fas fa-paper-plane"/></span>&nbsp;&nbsp;
        <span :style="style('fnGenerateEvents')" @click="fnGenerateEvents" title="Generate events at node"><i class="fas fa-stopwatch"/></span>
    </div>
    <div :id="id" v-if="nodeType==='zone'">
        <span :style="style('fnElectAggregator')" @click="fnElectAggregator" title="Start Aggregator election"><i class="fas fa-person-booth"/></span>&nbsp;
        <span :style="style('fnSendEventToClusterNodes')" @click="fnSendEventToClusterNodes" title="Send an event to all cluster nodes"><i class="fas fa-paper-plane"/></span>
    </div>
    <div :id="id" v-if="nodeType==='vm'">
        <span :style="style('fnSshConsole')" @click="fnSshConsole" title="SSH console"><i class="fas fa-terminal"/></span>&nbsp;&nbsp;
        <span :style="style('fnAppointAggregator')" @click="fnAppointAggregator" title="Appoint as Aggregator"><i class="fas fa-chess-knight"/></span>&nbsp;
        <span :style="style('fnSendEventToNode')" @click="fnSendEventToNode" title="Send an event to node"><i class="fas fa-paper-plane"/></span>&nbsp;
        <span :style="style('fnGenerateEvents')" @click="fnGenerateEvents" title="Generate events at node"><i class="fas fa-stopwatch"/></span>
    </div>
    <div :id="id" v-if="nodeType==='edge' || nodeType==='ignore'">
        <span :style="style('fnSshConsole')" @click="fnSshConsole" title="SSH console"><i class="fas fa-terminal"/></span>&nbsp;&nbsp;
    </div>
</template>

<script>
export default {
    name: 'Node Actions List widget',
    props: {
        id: String,
        nodeType: String,
        fnSshConsole: Function,
        fnAppointAggregator: Function,
        fnElectAggregator: Function,
        fnSendEventToNode: Function,
        fnSendEventToClusterNodes: Function,
        fnSendEventToAllNodes: Function,
        fnGenerateEvents: Function,
    },
    data() {
        return {
        };
    },
    methods: {
        style(fnName) {
            let active = (typeof this[fnName] === 'function');
            return active
                    ? { 'cursor': 'pointer' }
                    : { 'color': 'grey' };
        }
    }
}
</script>