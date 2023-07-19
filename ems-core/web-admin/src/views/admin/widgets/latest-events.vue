<!--
  ~ Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
  ~
  ~ This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
  ~ Esper library is used, in which case it is subject to the terms of General Public License v2.0.
  ~ If a copy of the MPL was not distributed with this file, you can obtain one at
  ~ https://www.mozilla.org/en-US/MPL/2.0/
  -->
<template>
    <table class="table table-striped table-sm"
           :style=" 'width: 100% !important; height: '+this.height+' !important; display: block; overflow-x: scroll; overflow-y: scroll;' "
    >
        <thead>
        <tr>
            <th>
                <div class="row">
                    <div class="col-1">Id</div>
                    <div class="col-2">Topic</div>
                    <div class="col-4">Payload</div>
                    <div class="col-3">properties</div>
                    <div class="col-2">Timestamp</div>
                </div>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(event) of events" :key="event.id">
            <td class="align-middle">
                <div class="row">
                    <div class="col-1">
                        {{event.counter}}
                    </div>
                    <div class="col-2">
                        {{event.destination}}
                    </div>
                    <div class="col-4">
                        {{event.payload}}
                    </div>
                    <div class="col-3">
                        {{event.properties}}
                    </div>
                    <div class="col-2">
                        {{new Date(event.timestamp).toISOString().replace('T',' ').substring(0,19)}}
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</template>

<script>
//const $ = require('jquery');

export default {
    name: 'Latest Events widget',
    props: {
        emsData: Object,
        height: String,
    },
    components: {
    },
    computed: {
        events: function() {
            if (!this.emsData || !this.emsData['broker-cep'] || !this.emsData['broker-cep']['latest-events']) return [];
            return [...this.emsData['broker-cep']['latest-events']].reverse();
        }
    },
    methods: {
    },
}
</script>