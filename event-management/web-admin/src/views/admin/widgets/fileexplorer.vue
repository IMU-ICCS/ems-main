<!--
  ~ Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
  ~
  ~ This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
  ~ Esper library is used, in which case it is subject to the terms of General Public License v2.0.
  ~ If a copy of the MPL was not distributed with this file, you can obtain one at
  ~ https://www.mozilla.org/en-US/MPL/2.0/
  -->
<template>
    <div class="row">
        <div class="col-3">
            <select id="root" class="form-control form-control-sm" v-model="rootId" v-on:change="rootChange" :disabled="disabled">
                <option v-for="(root, index) in roots" :key="index" :value="index">[{{index}}] {{root}}</option>
            </select>
        </div>
        <div class="col-9">
            <input id="path" class="form-control form-control-sm" v-model="path" v-on:change="pathChange" :disabled="disabled" />
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div style="margin: 10px 0px; min-height: 50px; height: 300px; border: 1px solid #ced4da; border-radius: .2rem; overflow: auto; resize: vertical;">
                <div  v-if="!disabled" class="table-responsive-sm">
                    <!--<span v-if="isRoot()" style="padding-left: 10px; color: grey; font-style: italic;">[ {{roots[rootId]}} ]</span>-->
                    <table id="files" class="table table-striped table-hover table-sm">
                        <thead>
                            <tr>
                                <th scope="col">File</th>
                                <th scope="col">Type</th>
                                <th scope="col">Size</th>
                                <th scope="col">Modified</th>
                                <th scope="col">Permissions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="! isRoot()">
                                <td><a href="javascript:void(0)" v-on:click="folderUp"><i class="fas fa-arrow-up" /> ..</a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr v-for="f in files" :key="f">
                                <td>
                                    <a v-if="!f.noLink" href="javascript:void(0)" v-on:click="fileClick(f)">{{f.path.substring(1)}}</a>
                                    <span v-else>{{f.path.substring(1)}}</span>
                                    <span v-if="f.hidden" style="color:darkred; font-size:small; font-style:italic;"> [Hidden]</span>
                                </td>
                                <td v-if="f.dir" style="color: grey; font-style: italic;" class="table-info">&lt;DIR&gt;</td>
                                <td v-else style="color: grey; font-style: italic;" class="text-center">{{getFileType(f)}}</td>
                                <td class="text-right">{{f.size}}</td>
                                <td>{{new Date(f.lastModified).toLocaleDateString('eu-EU')+' '+new Date(f.lastModified).toLocaleTimeString('eu-EU')}}</td>
                                <td align="center">[{{f.read?'r':'-'}}{{f.write?'w':'-'}}{{f.exec?'x':'-'}}]</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div v-else style="background-color: #e9ecef; width: 100%; height: 100%;"></div>
            </div>
        </div>
    </div>
</template>

<script>
const $ = require('jquery');

export default {
    name: 'File Explorer',
    data() {
        return {
            disabled: false,
            roots: [ ],
            rootId: 0,
            path: '',
            pathOk: '',
            files: [ ],
        };
    },
    mounted() {
        this.restCall('/files', (_this, responseText) => {
            if (responseText!=='') {
                let d = JSON.parse(responseText);
                if (Array.isArray(d)) {
                    _this.roots = _this.toPath(d);
                    _this.rootId = _this.roots.length>0 ? 0 : -1;
                    _this.rootChange();
                    return;
                }
            }
            _this.disabled = true;
            _this.roots = [];
            _this.rootId = -1;
            _this.path = _this.pathOk = '';
            _this.files = [ ];
        },
        (_this) => _this.disabled = true);
    },
    methods: {
        rootChange() {
            this.path = '';
            this.$nextTick(() => this.refreshFiles() );
        },
        pathChange() {
            this.refreshFiles();
        },
        pathNotFound(_this, xhr) {
            if (xhr.status==404) {
                alert('Path not found');
                _this.path = _this.pathOk;
                return true;
            }
            return false;
        },
        normalizePath(path) {
            return path.trim().replace(/^[\\/]+/, "");
        },
        isRoot() {
            return this.normalizePath(this.pathOk)==='';
        },
        refreshFiles() {
            let _rootId = this.rootId;
            let _path = this.normalizePath(this.path);
            this.restCall(
                '/files/dir/'+_rootId+'/'+_path,
                (_this, d) => {
                    if (d!=null && d!==null) {
                        try {
                            d = JSON.parse(d);
                            if (Array.isArray(d)) {
                                _this.pathOk = _path;
                                _this.files = d;
                                return;
                            }
                        } catch (e) {
                            console.error('Exception while processing server response: ', e, 'Server data: ', d);
                        }
                    }
                    _this.path = _this.pathOk;
                    alert('Error occurred while retrieving file list');
                },
                this.pathNotFound);
        },
        folderUp() {
            let _path = this.path;
            let _newPath = '';
            if (_path!=='') {
                let _p = _path.lastIndexOf('/');
                if (_p>0)
                    _newPath = _path.substring(0, _p);
            }
            this.path = _newPath;
            this.$nextTick(() => this.refreshFiles() );
        },
        fileClick(file) {
            let _newPath = this.normalizePath(this.path + file.path);
            if (file.dir) {
                // Open folder
                this.path = _newPath;
                this.pathChange();
            } else {
                // Download file
                this.downloadFile(this.rootId, _newPath);
            }
        },
        downloadFile(rootId, path) {
            try {
                var downloadLink = document.createElement('a');
                downloadLink.target = '_blank';
                downloadLink.href = '/files/get/'+rootId+'/'+path;
                document.body.append(downloadLink);
                downloadLink.click();
                document.body.removeChild(downloadLink);
            } catch (e) {
                console.error('Exception while downloading file: '+rootId+': '+path, e);
            }
        },
        getFileType(f) {
            let p1 = f.path.lastIndexOf('.');
            let p2 = f.path.lastIndexOf('/');
            if (p1>p2 && p1>-1) {
                return f.path.substring(p1+1).toLowerCase();
            }
            return '';
        },
        toPath(obj) {
            if (Array.isArray(obj))
                return obj.map(p => p.path);
            else
                return obj.path;
        },
        restCall(url, fnSuccess, fnError) {
            let _this = this;
            this.$nextTick(() => {
                $.ajax({
                    url: url,
                    complete: function(xhr,status) {
                        if (status==='success' && xhr.readyState==4) {
                            fnSuccess(_this, xhr.responseText);
                        } else {
                            let _show = true;
                            if (fnError) {
                                let _b = fnError(_this, xhr, status);
                                if (typeof _b === 'boolean')
                                    _show = ! _b;
                            }

                            if (_show) {
                                let _mesg = xhr.responseText;
                                try { let _r = JSON.parse(_mesg); if (_r && _r.message) _mesg = _r.message; } catch (e) { e; }
                                alert(` AJAX status: ${status} \n HTTP status: ${xhr.status} ${xhr.statusText} \n Message: \n ${_mesg}`);
                            }
                        }
                    }
                });
            });
        },
    },
}
</script>