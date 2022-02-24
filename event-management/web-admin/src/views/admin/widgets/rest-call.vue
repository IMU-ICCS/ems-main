<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="form-group row">
                    <label :for="'restEndpoint_'+uid"
                           class="col-sm-3 col-form-label"
                    >REST Endpoint</label>
                    <select :id="'restEndpoint_'+uid"
                            class="col-sm-9 form-control"
                            :aria-describedby="'restEndpointHelp_'+uid"
                    >
                        <option value="/camelModel">Send CAMEL model request</option>
                        <option value="/cpModelJson">Send CP model request</option>
                        <option value="/baguette/registerNode">Register Node</option>
                        <option value="/monitors">Get Monitors/Sensors</option>
                        <option value="/translator/currentCamelModel">Current CAMEL model</option>
                        <option value="/translator/currentCpModel">Current CP model</option>
                        <option value="/translator/constraintThresholds">Constraint Thresholds</option>
                        <option value="/broker/credentials">EMS server Broker credentials</option>
                        <option value="/baguette/stopServer">DEBUG - Stop Baguette Server</option>
                        <option value="/ems/shutdown">DEBUG - EMS server shutdown</option>
                        <option value="/ems/exit">DEBUG - EMS server shutdown and Exit</option>
                        <option value="/ems/exit/99">DEBUG - EMS server shutdown and Restart</option>
                        <option value="GET /health">Health check</option>
                    </select>
                    <!--<small :id="'restEndpointHelp_'+uid" class="form-text text-muted">Select an EMS Rest API endpoint to call.</small>-->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="form-group row">
                    <label :for="'restRequest_'+uid"
                           class="col-form-label"
                    >REST Request (JSON)</label>
                    <TextareaDnd :id="'restRequest_'+uid"
                                 class="form-control"
                                 :aria-describedby="'restRequestHelp_'+uid"
                                 placeholder="Request body in JSON"
                    />
                    <!--<small :id="'restEndpointHelp_'+uid" class="form-text text-muted">Provide the request body in JSON format.</small>-->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <button class="btn btn-xs btn-success float-right"
                        v-on:click="restCall"
                >
                    <i class="fa fa-paper-plane"></i>&nbsp;Send
                </button>
                <span v-if="showRestCallResultClear"
                      :id="'restCallResultClear_'+uid"
                      class="btn btn-xs btn-danger float-right"
                      v-on:click="()=>{showRestCallResult = false; showRestCallResultClear = false;}"
                >
                    <i class="fa fa-trash-alt"></i>&nbsp;Clear
                </span>
                <span v-if="showRestCallResult"
                      :id="'restCallResult_'+uid"
                      class="text-muted"/>
            </div>
        </div>
    </div>
</template>

<script>
const $ = require('jquery');
import TextareaDnd from './textarea-dnd.vue';

export default {
    name: 'Call EMS REST API widget',
    components: { TextareaDnd },
    data() {
        return {
            uid: Math.round(Math.random()*10000000) + new Date().getTime(),
            showRestCallResult: false,
            showRestCallResultClear: false,
        };
    },
    methods: {
        restCall() {
            let method = 'POST';
            let url = $('#restEndpoint_'+this.uid).val();
            if (url.indexOf(' ')>0) {
                let tmp = url.split(' ', 2);
                method = tmp[0];
                url = tmp[1];
            }
            //console.log(method+'  '+url);
            let body = $('#restRequest_'+this.uid).val();
            let _this = this;
            _this.showRestCallResult = true;
            this.$nextTick(() => {
                $('#restCallResult').html('<span style="color: grey;"><i class="fas fa-spinner fa-spin"></i> Contacting EMS server...</span>');
                $.ajax({
                    url: url,
                    type: method,
                    contentType: 'application/json',
                    data: body,
                    complete: function(xhr,status) {
                        //console.log('Call REST API: ', url, ' => ', status, xhr);
                        $('#restCallResult_'+_this.uid).html(`<b>Result:</b> ${status}<br/><b>Status:</b> ${xhr.status} ${xhr.statusText}<br/><b>Response:</b> ${xhr.responseText}`);
                        _this.showRestCallResultClear = true;
                    }
                });
            });
        },
    }
}
</script>