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

            <!-- CDO Repository Tree -->
            <div class="col-6 border rounded-lg">
                <div class="form-group row">
                    <div class="w-100 text-center text-nowrap bg-secondary text-white" style="margin-bottom: 0px;">
                        CDO repository&nbsp;&nbsp;
                        <button type="button" class="btn btn-tool text-white" data-toggle="tooltip" title="CDO Refresh" v-on:click="cdoRefresh()">
                            <i class="fas fa-sync" v-if="!cdoRefreshing"></i>
                            <div v-if="cdoRefreshing" class="spinner-border spinner-border-sm" role="status">
                                <span class="sr-only">Loading...</span>
                            </div>
                        </button>

                        <div class="float-right">
                            Filter:
                            <input id="cdo-repository-tree-filter" type="checkbox" checked data-toggle="toggle" />
                            &nbsp;&nbsp;&nbsp;
                        </div>
                    </div>
                </div>
                <small v-if="cdoTreeData==null"><i>Press &nbsp;<small class="text-muted"><i class="fas fa-sync"></i></small>&nbsp; button above to load CDO repository contents</i></small>
                <div v-if="cdoTreeError!==''" class="alert alert-danger">{{cdoTreeError}}</div>
                <div class="form-group" id="cdo-repository-tree">
                    <div v-for="(type,path) of cdoTreeData" :key="path">
                        <a href="javascript:void(0)" class="link-danger text-danger" v-on:click="cdoDelete(path)">
                            <i class="fas fa-times-circle"></i>
                        </a>
                        &nbsp;
                        <a v-if="getCdoItemLinkByType(type)" href="javascript:void(0)" class="link-primary" v-on:click="cdoExport(path)">{{path}}</a>
                        <span v-else>{{path}}</span>
                        <span class="float-right text-sm font-italic" style="color:grey;"><small>{{getCdoItemTextByType(type)}}</small> <img :src="getCdoItemIconByType(type)" width="18" height="18" /></span><br/>
                    </div>
                </div>
            </div>

            <!-- CDO Import Form -->
            <div class="col-6 border rounded">
                <div class="form-group row">
                    <p class="w-100 text-center text-nowrap bg-secondary text-white" style="margin-bottom: 0px;">Import a resource into CDO repository</p>
                </div>
                <form id="cdo-import-form" style="padding-left: 20px !important; padding-right: 20px !important;">
                    <div class="row">
                        <div v-if="cdoImportSuccess!==''" class="w-100 alert alert-success" role="alert">{{cdoImportSuccess}}</div>
                        <div v-if="cdoImportError!==''" class="w-100 alert alert-danger" role="alert">{{cdoImportError}}</div>
                    </div>
                    <div class="form-group row form-check form-check-inline">
                        <label for="cdo-import-resource-path" class="col-form-label col-form-label-sm">Resource:</label>
                        <input id="cdo-import-resource-path" class="form-control form-control-sm" type="text" name="cdo-import-resource-path" />
                        <small><i>Give the full path of the resource in CDO repository</i></small>
                    </div>
                    <div class="form-group row form-check form-check-inline">
                        <label for="cdo-import-operation-1" class="col-form-label col-form-label-sm">Import mode:</label>&nbsp;&nbsp;&nbsp;
                        <input id="cdo-import-operation-1" type="radio" name="cdo-import-operation" value="PUT" />&nbsp;
                        <label class="form-check-label col-form-label col-form-label-sm" for="cdo-import-operation-1">Create</label>&nbsp;&nbsp;&nbsp;
                        <input id="cdo-import-operation-2" type="radio" name="cdo-import-operation" value="POST" checked="checked" />&nbsp;
                        <label class="form-check-label col-form-label col-form-label-sm" for="cdo-import-operation-2">Create or Replace</label>
                    </div>
                    <div class="form-group row">
                        <label for="cdo-import-file" class="col-form-label col-form-label-sm">XMI file:</label>
                        <TextareaDnd id="cdo-import-text"
                                     name="cdo-import-text"
                                     class="form-control"
                                     placeholder="XMI content"
                        />
                        <small><i>Select an XMI file or paste the XMI content in the next area</i></small>
                        <input id="cdo-import-file" type="file" name="cdo-import-file"
                               class="form-control form-control-sm" accept=".xmi"
                               v-on:change="loadFile()"
                        />
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-6 text-center">
                            <button type="button" class="btn btn-primary btn-sm" v-on:click="cdoImport()">Import</button>
                        </div>
                        <div class="col-sm-6 text-center">
                            <button type="reset" class="btn btn-warning btn-sm" v-on:click="clearForm()">Clear</button>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</template>

<script>
const $ = require('jquery');
import TextareaDnd from './textarea-dnd.vue';

import iconCamelModel  from "./img/camel-model-32.png";
import iconCpModel  from "./img/cp-model-32.png";
import iconFolder  from "./img/folder-64.png";
import iconOther  from "./img/unknown-64.png";

export default {
    name: 'Manage CDO repository',
    components: { TextareaDnd },
    data() {
        return {
            uid: Math.round(Math.random()*10000000) + new Date().getTime(),
            cdoRefreshing: false,
            cdoTreeData: null,
            cdoTreeError: '',
            cdoImportSuccess: '',
            cdoImportError: '',
        };
    },
    methods: {
        cdoRefresh() {
            let filter = $('#cdo-repository-tree-filter').is(':checked');
            this.cdoRefreshing = true;
            this.cdoTreeError = '';
            this.callCdoEndpoint('/info/cdo?filter='+filter, 'LIST', null, null, null, null,
                (responseText) => {
                    this.cdoRefreshing = false;
                    this.cdoTreeData = $.parseJSON(responseText);
                },
                (error) => {
                    this.cdoRefreshing = false;
                    this.cdoTreeError = error;
                });
        },
        getCdoItemLinkByType(type) {
            let link = false;
            if (type==='CamelModel') link = true;
            if (type==='ConstraintProblem') link = true;
            return link;
        },
        getCdoItemIconByType(type) {
            let icon = iconOther;
            if (type==='CamelModel') icon = iconCamelModel;
            if (type==='ConstraintProblem') icon = iconCpModel;
            if (type==='FOLDER') icon = iconFolder;
            return icon;
        },
        getCdoItemTextByType(type) {
            let text = '';
            if (type==='CamelModel') text = 'Camel';
            if (type==='ConstraintProblem') text = 'CP';
            return text;
        },

        loadFile() {
            var inp = $('#cdo-import-file');
            var txt = $('#cdo-import-text').val();

            if (txt==null || txt.trim()==='' || txt!=null && txt.trim()!=='' && confirm('Do you want to replace current text area content?')) {
                let files = inp[0].files;
                if (files.length > 0) {
                    let file = files[0];
                    let reader = new FileReader();
                    reader.onload = function(event) {
                        $('#cdo-import-text').val(event.target.result);
                    };
                    reader.readAsText(file);
                }
            }

            // Clear selected file
            //inp.val(null);
        },

        cdoImport() {
            var inputs = $('#cdo-import-form :input');
            var values = {};
            inputs.each(function() {
                if (this.name==='cdo-import-operation') {
                    if (this.checked)
                        values[this.name] = $(this).val();
                } else {
                    values[this.name] = $(this).val();
                }
            });
            //console.log('CDO import form - Values: ', values);

            let path = values['cdo-import-resource-path'].trim();
            let op = values['cdo-import-operation'].trim();
            let xmi = values['cdo-import-text'];

            var error = false;
            if (path==='') { error = true; alert('You must provide resource path in "Resource" field'); }
            if (op==='') { error = true; alert('You must select an "Import mode"'); }
            if (op!=='PUT' && op!=='POST') { error = true; alert('Invalid "Import mode": '+op); }
            if (xmi.trim()==='') { error = true; alert('You must select an "XMI file" or enter XMI content in text area'); }
            if (error) return;

            this.cdoImportSuccess = '';
            this.cdoImportError = '';

            inputs.prop('disabled', true);
            this.callCdoEndpoint('/info/cdo/'+path, 'IMPORT', op, 'text/xml', xmi, null,
                () => {
                    this.cdoImportSuccess = 'Success';
                    this.clearForm();
                    this.cdoRefresh();
                    inputs.prop('disabled', false);
                },
                (error) => {
                    this.cdoImportError = error;
                    inputs.prop('disabled', false);
                });
        },
        cdoExport(path) {
            this.cdoTreeError = '';
            this.callCdoEndpoint('/info/cdo/'+path, null, 'GET', null, null, 'text',
                    (responseText) => this.saveToFile(this.createFileName(path), responseText),
                    (error) => this.cdoTreeError = error);
        },
        cdoDelete(path) {
            if (confirm('Delete resource from CDO repository?\n\n'+path)) {
                this.cdoTreeError = '';
                this.callCdoEndpoint('/info/cdo/'+path, null, 'DELETE', null, null, null,
                    (responseText) => {
                        if (responseText!=='OK')
                            this.cdoTreeError = responseText;
                        else
                            this.cdoRefresh();
                    },
                    (error) => this.cdoTreeError = error);
            }
        },
        clearForm() {
            $('#cdo-import-form').trigger("reset");
            this.cdoImportSuccess = '';
            this.cdoImportError = '';
        },

        callCdoEndpoint(url,descr,method,contentType,data,dataType,fnSuccess,fnError) {
            this.$nextTick(() => {
                if (descr && descr.trim()!=='') descr = ' for '+descr; else descr = '';
                if (descr==='' && method && method.trim()!=='') descr = ' for '+method.trim();

                let _this = this;
                let options = {
                    url: url,
                    complete: function(xhr, status) {
                        status;
                        //console.log('Call completed to CDO REST endpoint'+descr+': ', url, ' => ', status, xhr);
                        if (xhr.readyState==4 && xhr.status>=200 && xhr.status<300) {
                            fnSuccess( xhr.responseText );
                        } else {
                            fnError( _this.createErrorMessage(xhr) );
                        }
                    }
                };
                if (method) options['type'] = method;
                if (contentType) options['contentType'] = contentType;
                if (data) options['data'] = data;
                if (dataType) options['dataType'] = dataType;

                //console.log('Calling CDO REST endpoint'+descr+'...', url);
                $.ajax(options);
            });
        },
        createErrorMessage(xhr) {
            var responseText = xhr.responseText;
            try {
                if (xhr.responseText) {
                    var r = $.parseJSON(xhr.responseText);
                    if (r.message) {
                        r = r.message.split('\n');
                        var rr = [];
                        rr.push(r[0]);
                        for (var i=1; i<r.length; i++) {
                            if (r[i].trim().startsWith('Caused')) {
                                rr.push(r[i].trim());
                            }
                        }
                        responseText = rr.join('\n');
                    }
                }
            } catch (e) { e; }
            return `An error occurred: status=${status}, readyState=${xhr.readyState}, response=${responseText}`;
        },

        createFileName(path) {
            let base = path[0]==='/' ? path.substring(1) : path;
            base = base.replace(/[^\w]/gi,'_');
            let tm = new Date().getTime();
            let ext = 'xmi';
            return `${base}_${tm}.${ext}`;
        },
        saveToFile(fileName, textData){
            let blobData = new Blob([textData], {type: "text/xml"});
            let url = window.URL.createObjectURL(blobData);

            let a = document.createElement("a");
            a.style = "display: none";
            document.body.appendChild(a);
            a.href = url;
            a.download = fileName;
            a.click();
            window.URL.revokeObjectURL(url);
            a.remove();
        }
    }
}
</script>