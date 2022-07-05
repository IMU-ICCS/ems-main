<!--
  ~ Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
  ~
  ~ This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
  ~ Esper library is used, in which case it is subject to the terms of General Public License v2.0.
  ~ If a copy of the MPL was not distributed with this file, you can obtain one at
  ~ https://www.mozilla.org/en-US/MPL/2.0/
  -->
<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="form-group row">
                    <label :for="'formType_'+uid"
                           class="col-sm-3 col-form-label"
                    >Request type</label>
                    <select :id="'formType_'+uid"
                            class="col-sm-9 form-control"
                            :aria-describedby="'restEndpointHelp_'+uid"
                            v-on:change="changeForm"
                    >
                        <option v-for="opt in options" v-bind:value="opt.id" :key="opt.id">{{opt.text}}</option>
                    </select>
                    <!--<small :id="'restEndpointHelp_'+uid" class="form-text text-muted">Select an EMS Rest API endpoint to call.</small>-->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="form-group row">
                    <label :for="'restEndpoint_'+uid"
                           class="col-sm-3 col-form-label"
                    >REST Endpoint</label>
                    <input :id="'restEndpoint_'+uid"
                           v-model="formData.endpoint"
                           class="col-sm-9 form-control"
                           :aria-describedby="'restEndpointHelp_'+uid"
                           readonly="readonly"
                    />
                </div>
            </div>
        </div>
        <!-- Variable form fields -->
        <div class="row" v-for="f of form[formSelected].fields" :key="f.name">
            <div class="col-12">
                <div class="form-group row">
                    <label :for="get_input_id(f)"
                           class="col-sm-3 col-form-label"
                    >{{f.text}}</label>
                    <input :id="get_input_id(f)"
                           :value="get_form_data(f)"
                           class="col-sm-9 form-control"
                           :aria-describedby="f.name+'_'+uid"
                           v-on:change="updateFieldAndData(f)"
                    />
                </div>
            </div>
        </div>
        <!-- Request payload -->
        <div class="row">
            <div class="col-12">
                <div class="form-group row">
                    <label :for="'restRequestPayload_'+uid"
                           class="col-form-label"
                    >Request Payload (JSON)</label>
                    <TextareaDnd :id="'restRequestPayload_'+uid"
                                 class="form-control"
                                 :aria-describedby="'restRequestHelp_'+uid"
                                 placeholder="Request body in JSON"
                                 rows="10"
                                 v-on:change="updateDataFromPayload()"
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

import { FORM_TYPE_OPTIONS, FORM_SPECS } from './rest-call-forms.js';


export default {
    name: 'Call EMS REST API widget',
    components: { TextareaDnd },
    props: {
        rootId: String,
    },
    data() {
        return {
            uid: Math.round(Math.random()*10000000) + new Date().getTime(),
            showRestCallResult: false,
            showRestCallResultClear: false,
            options: FORM_TYPE_OPTIONS,
            formSelected: '',
            formData: {},
            form: FORM_SPECS,
        };
    },
    mounted: function() {
        this.changeForm({ target: { value: 'new-camel' }});
        this.$root[this.rootId] = this;
    },
    methods: {
        switchToForm(form, data) {
            if (!confirm('Update REST call pane?')) return;

            if (data) {
                let m = new Map(Object.entries(data));
                let _this = this;
                m.forEach((value, key) => {
                    let _id = _this.get_input_id({ name: key });
                    _this.formData[_id] = value;
                });
            }

            this.changeForm({ target: { value: form }})
        },
        changeForm(e) {
            let selId = e.target.value;
            for (let opt of this.options) {
                if (opt.id===selId) {
                    this.formSelected = opt.form;
                    this.formData.endpoint = opt.url;
                    break;
                }
            }
            this.$nextTick(() => {
                $('#formType_'+this.uid).val(selId);
                $('#restRequestPayload_'+this.uid).val('');
                this.updatePayload();
            });
        },
        get_input_id(f) {
            let _id = f.name.replace(/\./gi,'_')+'_'+this.uid;
            return _id;
        },
        get_form_data(f) {
            let _id = this.get_input_id(f);
            let _val = this.formData[_id] ?? (typeof f.defaultValue==='function' ? f.defaultValue(this) : f.defaultValue);
            if (!_val) _val = '';
            this.formData[_id] = _val;
            return _val;
        },
        create_UUID() {
            var dt = new Date().getTime();
            var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                var r = (dt + Math.random()*16)%16 | 0;
                dt = Math.floor(dt/16);
                return (c=='x' ? r :(r&0x3|0x8)).toString(16);
            });
            return uuid;
        },
        updateFieldAndData(f) {
            // Update form data
            if (f && f!=null) {
                let _id = this.get_input_id(f);
                this.formData[_id] = $('#'+_id).val();
            }

            // Update payload
            this.updatePayload();
        },
        updatePayload() {
            // Update endpoint (if it contains placeholders)
            this.updateEndpoint();

            // Update request payload
            let taPayload = $('#restRequestPayload_'+this.uid);
            let type = $('#formType_'+this.uid).val();
            let opt = this.options.find((opt) => opt.id===type);
            let fields = this.form[opt.form].fields;
            let s = taPayload.val();
            let obj = s.trim()==='' ? {} : JSON.parse(s);
            let _this = this;

            $.each(fields, function(i, f) {
                let v = _this.get_form_data(f);
                let parts = f.name.split('.');
                let _o = obj;
                while (parts.length>1) {
                    let _p = parts.shift();
                    if (!_o[_p]) _o[_p] = {};
                    _o = _o[_p];
                }
                _o[parts[0]] = v;
            });
            s = JSON.stringify(obj, null, 4);
            taPayload.val(s);
        },
        updateEndpoint() {
            let type = $('#formType_'+this.uid).val();
            let source = this.options.find((opt) => opt.id===type).url;
            let suffix = '_'+this.uid;
            $.each(this.formData, function (k, v) {
                let kk = k.endsWith(suffix) ? k.replace(suffix,'') : k;
                source = source.replace(new RegExp(`{${kk}}`, "g"), v);
            })
            this.formData.endpoint = source;
            return source;
        },
        updateDataFromPayload() {
            let taPayload = $('#restRequestPayload_'+this.uid);
            let type = $('#formType_'+this.uid).val();
            let opt = this.options.find((opt) => opt.id===type);
            let fields = this.form[opt.form].fields;
            let s = taPayload.val();
            let obj = s.trim()==='' ? {} : JSON.parse(s);
            let _this = this;

            // Update form data
            $.each(fields, function(i, f) {
                let parts = f.name.split('.');
                let _o = obj;
                while (parts.length>1) {
                    let _p = parts.shift();
                    if (_o[_p]) {
                        _o = _o[_p];
                    } else {
                        _o = null;
                        break;
                    }
                }
                if (_o && _o!=null) {
                    let v = _o[parts[0]];
                    let _id = _this.get_input_id(f);
                    _this.formData[_id] = v;
                    $('#'+_id).val(v);
                }
            });

            // Update endpoint
            this.updateEndpoint();
        },
        restCall() {
            let _form = $('#formType_'+this.uid).val();
            if (!_form || _form==='') return;
            let _opt = this.options.find(opt => opt.id===_form);
            console.log('##### ', _opt);

            let method = _opt.method;
            let url = $('#restEndpoint_'+this.uid).val();
            //console.log(method+'  '+url);
            let body = $('#restRequestPayload_'+this.uid).val();
            if (method.toUpperCase()!=='POST' && method.toUpperCase()!=='PUT')
                body = null;

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