<template>
  <!-- Main content -->
  <section class="content" style="padding: 0;">
    <div class="container-fluid" style="padding: 0;">
      <Section title="Overview" background="rgba(173,216,230,.6)" :collapsed="true" @expanded="showOverviewHeader=false" @collapsed="showOverviewHeader=true">
          <template v-slot:header>
              <OverviewSectionHeader v-model="ems" :sseInfo="modelValue.sseInfo" :timeseries="sysmonTimeseries" :showHeader="showOverviewHeader" />
          </template>

          <span style="color: darkblue;">(EMS load, EMS state, Events, Clients, Topology Health?)</span>

          <OverviewSection v-model="ems" />
      </Section>

      <Section title="Topology" :collapsed="false" background="rgba(0,255,100,.3)">
          <TopologySection v-model="ems" />
      </Section>

      <!--<Section title="Geography" background="rgba(250,170,0,.25)">
          <GeographySection v-model="ems" />
      </Section>-->

      <Section title="Commands" background="rgba(255,0,0,.5)">
          <CommandsSection v-model="ems" />
      </Section>

      <!--<Section title="Topics and CEP" background="rgba(255,0,0,.55)">
          (Topic list per Level, Topics Graph, Rules per Level)
          <BrokerCepSection v-model="ems" />
      </Section>

      <Section title="Miscellaneous 1" background="rgba(64,64,190,.6)">......</Section>
      <Section title="Miscellaneous 2" background="rgba(250,170,0,.75)">......</Section>
      <Section title="Miscellaneous 2" background="rgba(230,230,0,.25)">......</Section>
      <Section title="Debug form &#45;&#45; Overlay?"> ????? </Section>-->
    </div>
  </section>
  <!-- /.content -->
</template>

<script>
import Section from '@/views/common/section/section.vue';

import OverviewSection from './admin-1-overview.vue';
import OverviewSectionHeader from './admin-1-overview-header.vue';
import TopologySection from './admin-2-topology.vue';
//import GeographySection from './admin-3-geography.vue';
import CommandsSection from './admin-4-commands.vue';
//import BrokerCepSection from './admin-5-broker-cep.vue';

import utils from '@/utils.js';
import { TimeWindow } from '@/components/ems/ts/ts.js';

const TIME_WINDOW_LENGTH = 5*60;  // seconds

export default {
    name: 'Admin Dashboard',
    props: {
        modelValue: Object,
        sseRef: String
    },
    components: { Section, OverviewSection, OverviewSectionHeader, TopologySection, /*GeographySection,*/ CommandsSection, /*BrokerCepSection,*/ },
    mounted() {
        if (!this.sseRef || this.sseRef.trim()==='')
            throw "Property 'sseRef' is undefined in 'admin.vue'";
        let sseInterval = this.$root.$refs[this.sseRef].getCurrentInterval();
        this.sysmonTimeseries = new TimeWindow(TIME_WINDOW_LENGTH, sseInterval);
    },
    watch: {
        modelValue: {
            immediate: true,
            handler(newVal) {
                console.log('EMS-SSE data updated');
                if (newVal && newVal.data) {
                    if (newVal.data.ems) this.ems = newVal.data.ems;
                    //if (newVal.data.clients) this.clients = newVal.data.clients;

                    if (this.sysmonTimeseries!=null && this.ems && utils.valueExists(this.ems, 'system-info.system-resource-metrics')) {
                        let sysmonData = utils.getValue(this.ems, 'system-info.system-resource-metrics');
                        this.sysmonTimeseries.add(sysmonData);
                    }
                }
            },
        },
        ems: function(newVal) {
            if (!newVal) return;
        },
        clients: function(newVal) {
            if (!newVal) return;
        },
    },

    data() {
        return {
            showOverviewHeader: true,
            ems: { },
            sysmonTimeseries: null,
        };
    },
}
</script>

<style scoped></style>
