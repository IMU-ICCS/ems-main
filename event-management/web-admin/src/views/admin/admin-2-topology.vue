<template>
    <div class="row">
      <div class="col-7">
          <!-- Default box -->
          <Card bodyClasses="table-responsive p-0"
                header="Baguette Clients"
                icon="fas fa-th-list"
                :hasRefresh="true" :hasCollapse="true" :hasMaximize="true" :hasRemove="true"
                runRefresh="alert('Do refresh.\nHeader: '+this.header)"
          >
              <!--<template v-slot:title>
                  <div class="float-right input-group input-group-sm" style="width: 250px;">
                      <input type="text" name="table_search" class="form-control float-right" placeholder="Search">
                      <div class="input-group-append">
                          <button type="submit" class="btn btn-default">
                              <i class="fas fa-search"></i>
                          </button>
                      </div>
                  </div>
              </template>-->

              <table class="table table-striped table-sm">
                  <thead>
                  <tr>
                      <th style="width: 30px" nowrap>Client Id</th>
                      <th style="width: 190px">Node/Zone</th>
                      <th style="width: 90px">Address/Location</th>
                      <th style="width: 40%">Statistics</th>
                      <th style="width: 30px">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="c in clients" v-bind:key="c.id">
                      <td class="align-middle text-center" :title="c.id"><span v-if="c.id.startsWith('#')">{{c.id}}</span><i v-else class="fas fa-ban"/></td>
                      <td class="align-middle">
                          {{c.name}}
                          <span v-if="c.id.startsWith('#')" :class="['badge', 'pull-right', `${c.status=='Up'?'bg-success':c.status=='Down'?'bg-danger':'bg-warning'}`]">{{c.status??'Unknown'}}</span>
                          <small style="color: lightgrey; font-style: italic;">{{c.nodeId}}</small>
                      </td>
                      <td class="align-middle">
                          {{c.address}}:{{c.port}}
                          <!--<span v-if="c.country && c.country!==''"><br/><small>{{c.country??'-'}}</small></span>
                          <br/>
                          <small>Lat/Lon: {{c.lat??'-'}}, {{c.lon??'-'}}</small>-->
                          <br/>
                          <span :class="['badge', 'badge-pill', getClusterStatusClass(c.nodeStatus)]">{{getClusterStatus(c.nodeStatus)}}</span>
                      </td>

                      <td class="align-middle text-center">
                          <div class="row p-0 m-0">
                              <div class="col-md-3 m-0 border-left border-right">
                                  <small>CPU:</small>
                                  <Sparkline type="bullet" width="100%" height="10px" :values="[.7,c.stats.cpu,1]"></Sparkline>
                              </div>
                              <div class="col-md-3 m-0 border-left border-right">
                                  <small>Mem:</small>
                                  <Sparkline type="bullet" width="100%" height="10px" :values="[.8,c.stats.mem,1]"></Sparkline>
                              </div>
                              <div class="col-md-3 m-0 border-left border-right">
                                  <small style="white-space: pre;">#Events:</small><br/>
                                  <Sparkline type="line" width="100%" height="20px" :values="[0,0,10,8,0]"></Sparkline>
                              </div>
                              <div class="col-md-3 m-0 border-left border-right">
                                  <small style="white-space: nowrap;">Uptime:</small><br/>
                                  <small style="white-space: nowrap;">{{c.stats.uptime ? "toIsoFormat(c.stats.uptime,'sec','time')" : '--:--:--'}}</small>
                              </div>
                          </div>
                      </td>
                      <td class="align-middle text-center">
                          <ActionsList id="" :nodeType="c.tree_node_type"
                                       :fnSshConsole="()=>openSshConsole(c.reference, c.address)"
                                       :fnAppointAggregator="()=>appointAggregator(c.id, c.nodeId)"
                          />
                      </td>
                  </tr>
                  </tbody>
              </table>

              <template v-slot:footer>
                  <div class="row">
                      <!--<div class="col-md-5">
                          <div class="dataTables_info" id="example1_info" role="status" aria-live="polite">
                              <span style="font-size:.95em;">Showing 1 to 10 of 57 entries</span>
                          </div>
                      </div>
                      <div class="col-md-7">
                          <div class="clearfix">
                              <ul class="pagination pagination-sm m-0 float-right">
                                  <li class="page-item"><a class="page-link" href="#">«</a></li>
                                  <li class="page-item"><a class="page-link" href="#">1</a></li>
                                  <li class="page-item"><a class="page-link" href="#">2</a></li>
                                  <li class="page-item"><a class="page-link" href="#">3</a></li>
                                  <li class="page-item"><a class="page-link" href="#">»</a></li>
                              </ul>
                          </div>
                      </div>-->
                  </div>

              </template>
          </Card>
      </div>
      <!-- /.col-md-7 -->

      <div class="col-5">
          <!-- Default box -->
          <Card bodyClasses="table-responsive p-0"
                header="Local clusters"
                icon="fas fa-th-list"
                :hasRefresh="true" :hasCollapse="true" :hasMaximize="true" :hasRemove="true"
                runRefresh="alert('Do refresh.\nHeader: '+this.header)"
          >
              <!--<template v-slot:title>
                  <div class="float-right input-group input-group-sm" style="width: 250px;">
                      <input type="text" name="table_search" class="form-control float-right" placeholder="Search">
                      <div class="input-group-append">
                          <button type="submit" class="btn btn-default">
                              <i class="fas fa-search"></i>
                          </button>
                      </div>
                  </div>
              </template>-->

              <table class="table table-striped table-sm">
                  <thead>
                  <tr>
                      <!--<th style="width: 90px">Id</th>-->
                      <th style="width: 190px">Cluster/Location</th>
                      <th style="width: 90px">Provider</th>
                      <th style="width: 40%">Statistics</th>
                      <th style="width: 30px">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="c in clusters" v-bind:key="c.id">
                      <!--<td class="align-middle">{{c.id}}</td>-->
                      <td class="align-middle">
                          {{c.id}}
                          <span v-if="c.id!=='IGNORED'" :class="['badge', 'pull-right', `${c.status=='Up'?'bg-success':c.status=='Down'?'bg-danger':'bg-warning'}`]">{{c.status??'Unknown'}}</span>
                          <br/><small>
                              <!--{{c.country??'-'}}<br/>-->
                              <!--Lat/Lon: {{c.lat??'-'}}, {{c.lon??'-'}}-->
                          </small>
                      </td>
                      <td class="align-middle">{{c.provider}}</td>
                      <td class="align-middle text-center">
                          <div class="row p-0 m-0">
                              <div class="col-md-6 m-0 border-left border-right">
                                  <small style="white-space: pre;">#Events:</small><br/>
                                  <Sparkline type="line" width="100%" height="20px" :values="[0,0,10,8,0]"></Sparkline>
                              </div>
                              <div class="col-md-6 m-0 border-left border-right">
                                  <small style="white-space: nowrap;">Uptime:</small><br/>
                                  <small style="white-space: nowrap;">{{c.stats.uptime ? 'xx:xx:xx' : '--:--:--'}}</small>
                              </div>
                          </div>
                      </td>
                      <td class="align-middle text-center">
                          <ActionsList id="" nodeType="zone" v-if="c.id!=='IGNORED'"
                                       :fnElectAggregator="()=>electAggregator(c)"
                          />
                      </td>
                  </tr>
                  </tbody>
              </table>

              <template v-slot:footer>
                  <div class="row">
                      <!--<div class="col-md-8">
                          <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">
                              <span style="font-size:.95em;">Showing 1 to 10 of 57 entries</span>
                          </div>
                      </div>
                      <div class="col-md-4">
                          <div class="clearfix">
                              <ul class="pagination pagination-sm m-0 float-right">
                                  <li class="page-item"><a class="page-link" href="#">«</a></li>
                                  <li class="page-item"><a class="page-link" href="#">1</a></li>
                                  <li class="page-item"><a class="page-link" href="#">2</a></li>
                                  <li class="page-item"><a class="page-link" href="#">3</a></li>
                                  <li class="page-item"><a class="page-link" href="#">»</a></li>
                              </ul>
                          </div>
                      </div>-->
                  </div>

              </template>
          </Card>
      </div>
      <!-- /.col-md-5 -->
    </div>

    <div class="row">
      <div class="col-12">
          <!-- Default box -->
          <Card bodyClasses="table-responsive p-0"
                header="&nbsp;&nbsp;Topology Graph"
                icon="fas fa-project-diagram"
                :hasRefresh="true" :hasCollapse="true" :hasMaximize="true"
                :runRefresh="refreshTree()"
          >
              <p style="text-align: center;">
                <vue-blocks-tree  :data="treeData2" :horizontal="false" :collapsable="true" :key="someVariableUnderYourControl">
                    <template #node="{data}">
                      <div v-if="data.tree_node_type=='ems'" style="border: 2px solid red; margin: 0px; padding: 3px;">
                          <div class="tree-node-head" style="background-color: rgba(255,0,0,.1);"><i class="fas fa-home"></i>&nbsp;{{data.label}}</div>
                          <small>Address: {{emsServerLocation()}}</small><br/>
                          <small>Clusters: {{data.totalClusters}}&nbsp;&nbsp;&nbsp;Nodes: {{data.totalClients}}</small>
                          <br/>
                          <ActionsList id="" :nodeType="data.tree_node_type" />
                      </div>
                      <div v-if="data.tree_node_type=='zone'">
                          <div class="tree-node-head" :style="[data.id!=='IGNORED' ? 'background-color: #d0ffff;' : 'background-color: #eeeeee;']"><i :class="[data.id!=='IGNORED' ? 'fas fa-cloud' : 'far fa-times-circle']"></i>&nbsp;{{data.label}}</div>
                          <small>&nbsp;</small>
                          <!--<small>Provider: ++TODO++
                          <br/>
                              Network: ++TODO++</small>-->
                          <small>Nodes: {{data.totalClients}}</small>
                          <br/>
                          <ActionsList id="" :nodeType="data.tree_node_type" v-if="data.id!=='IGNORED'"
                                       :fnElectAggregator="()=>electAggregator(data)"
                          />
                          <br v-else/>
                      </div>
                      <div v-if="data.tree_node_type=='vm'" :style="data.nodeStatus==='AGGREGATOR' ? 'border: 4px solid magenta; margin: 0px; padding: 3px;' : 'border: 2px solid green; margin: 0px; padding: 3px;'">
                          <div class="tree-node-head" style="background-color: transparent;"><i class="fas fa-server"></i>&nbsp;{{getShorterNodeName(data.label)}}</div>
                          <small style="color: lightgrey; font-style: italic;">{{data.nodeId}}</small>
                          <br/>
                          <small>Address: {{data.address}}:{{data.port}}</small>
                          <br/>
                          <span :class="['badge', 'badge-pill', getClusterStatusClass(data.nodeStatus)]">{{getClusterStatus(data.nodeStatus)}}</span>
                          <br/>
                          <ActionsList id="" :nodeType="data.tree_node_type"
                                       :fnSshConsole="()=>openSshConsole(data.reference, data.address)"
                                       :fnAppointAggregator="()=>appointAggregator(data.id, data.nodeId)"
                          />
                      </div>
                      <div v-if="data.tree_node_type=='edge'" style="border: 3px dashed orange; margin: 0px; padding: 3px; orange; background-image: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0naHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmcnIHdpZHRoPScxMCcgaGVpZ2h0PScxMCc+CiAgPHJlY3Qgd2lkdGg9JzEwJyBoZWlnaHQ9JzEwJyBmaWxsPSd3aGl0ZScgLz4KICA8cmVjdCB4PScwJyB5PScwJyB3aWR0aD0nMScgaGVpZ2h0PScxJyBmaWxsPSdibGFjaycgLz4KPC9zdmc+'); background-repeat: repeat;">
                          <div class="tree-node-head" style="background-color: rgba(255,165,0,.4);"><i class="fas fa-sim-card"></i>&nbsp;{{getShorterNodeName(data.label)}}</div>
                          <small style="color: lightgrey; font-style: italic;">{{data.nodeId}}</small>
                          <br/>
                          <small>&nbsp;&nbsp;&nbsp;&nbsp;Address: {{data.address}}&nbsp;&nbsp;&nbsp;&nbsp;</small>
                          <br/>
                          <span :class="['badge', 'badge-pill', getClusterStatusClass(data.nodeStatus)]">{{getClusterStatus(data.nodeStatus)}}</span>
                          <br/>
                          <ActionsList id="" :nodeType="data.tree_node_type"
                                       :fnSshConsole="()=>openSshConsole(data.reference, data.address)"
                          />
                      </div>
                      <div v-if="data.tree_node_type=='ignore'" style="border: 4px dotted grey; margin: 0px; padding: 3px; background-image: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0naHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmcnIHdpZHRoPSc1JyBoZWlnaHQ9JzUnPgo8cmVjdCB3aWR0aD0nNScgaGVpZ2h0PSc1JyBmaWxsPScjZmZmJy8+CjxyZWN0IHdpZHRoPScxJyBoZWlnaHQ9JzEnIGZpbGw9JyNjY2MnLz4KPC9zdmc+'); background-repeat: repeat;">
                          <div class="tree-node-head" style="background-color: #eeeeee;"><i class="far fa-times-circle"></i>&nbsp;{{getShorterNodeName(data.label)}}</div>
                          <br/>
                          <small>&nbsp;&nbsp;&nbsp;&nbsp;Address: {{data.address}}&nbsp;&nbsp;&nbsp;&nbsp;</small>
                          <br/>
                          <span :class="['badge', 'badge-pill', getClusterStatusClass(data.nodeStatus)]">{{getClusterStatus(data.nodeStatus)}}</span>
                          <br/>
                          <ActionsList id="" :nodeType="data.tree_node_type"
                                       :fnSshConsole="()=>openSshConsole(data.reference, data.address)"
                          />
                      </div>
                    </template>
                </vue-blocks-tree>
            </p>
          </Card>
      </div>
      <!-- /.col-md-6 -->

    </div>

    <div class="row">
        <div class="col-6">
            <Card bodyClasses="p-0"
                  header="&nbsp;Client Locations (with Leaflet)"
                  icon="fas fa-map"
                  :hasCollapse="true" :hasMaximize="true" :hasRemove="true"
            >
                <LeafletMap :mapType="modelValue['LEAFLET_MAP_TYPE']"
                            :mapTilesUrl="modelValue['LEAFLET_MAP_TILES_URL']"
                            :mapTilesConfig="{
                                maxZoom: modelValue['LEAFLET_MAP_ZOOM'],
                                attribution: modelValue['LEAFLET_MAP_ATTRIBUTION']
                            }"
                            :style="{ height: '425px' }"
                            :markers="clientMarkers"
                            :markerTypes="{
                                    ems: { markerColor: 'red' },
                                    AGGREGATOR: { markerColor: 'magenta' },
                                    CANDIDATE: { markerColor: 'cyan' },
                                    NOT_CANDIDATE: { markerColor: 'orange' },
                                    IGNORED: { markerColor: 'black' },
                            }"
                            :markerConnections="clientConnections"
                ></LeafletMap>

                <template v-slot:footer>
                    <div class="row" style="color:grey;">
                        <i class="fas fa-map-marker-alt" style="color: red;" />&nbsp;<span class="align-text-top small">EMS Server</span>&nbsp;&nbsp;
                        <i class="fas fa-map-marker-alt" style="color: magenta;"/>&nbsp;<span class="align-text-top small">Aggregator</span>&nbsp;&nbsp;
                        <i class="fas fa-map-marker-alt" style="color: cyan;"/>&nbsp;<span class="align-text-top small">Candidate</span>&nbsp;&nbsp;
                        <i class="fas fa-map-marker-alt" style="color: green;"/>&nbsp;<span class="align-text-top small">Initializing</span>&nbsp;&nbsp;
                        <i class="fas fa-map-marker-alt" style="color: orange;"/>&nbsp;<span class="align-text-top small">Not Candidate</span>&nbsp;&nbsp;
                        <i class="fas fa-map-marker-alt" style="color: black;"/>&nbsp;<span class="align-text-top small">Ignored</span>&nbsp;&nbsp;
                        <div style="right:0; position:absolute; margin-right: 15px;">
                            <i class="fas fa-trash" role="button" title="Clear geo-locations cache" @click="clearGeolocationCache()"></i>
                        </div>
                    </div>
                </template>
            </Card>
        </div>
        <!-- /.col-6 -->

        <div class="col-6">
            <Card bodyClasses="p-0"
                  header="&nbsp;Client Locations (with JVectormap)"
                  icon="fas fa-map"
                  footer="..."
                  :hasCollapse="true" :hasMaximize="true" :hasRemove="true"
            >
                <JVectorMap height="425px"
                            :options="{ /*backgroundColor:'navy'*/ }"
                            :markers="clientMarkers"
                ></JVectorMap>
            </Card>
        </div>
        <!-- /.col-6 -->
    </div>

    <!-- SSH console modal -->
    <div id="ssh-console-dialog" title="SSH console" style="overflow: hidden; width: 90vw !important; height: 90% !important; padding: 0px; display: flex; align-items: stretch;">
        <!--<object id="ssh-console-object" type="text/html" data="" style="overflow: hidden; border: 5px ridge blue; width: 100% !important; height: 100% !important;" />-->
    </div>
</template>

<script>
const $ = require('jquery');
import 'jquery-ui';
import 'jquery-ui/ui/widgets/dialog.js';
import 'jquery-ui/themes/base/all.css';

import Card from '@/components/card/card.vue';
import Sparkline from '@/components/sparkline/sparkline.vue';
import VueBlocksTree from 'vue3-blocks-tree';
import 'vue3-blocks-tree/dist/vue3-blocks-tree.css';

import ActionsList from './widgets/node-actions-list';

import JVectorMap from '@/components/jvectormap/jvectormap.vue';
//import utils from '@/utils.js';

import LeafletMap from '@/components/leaflet-map/leaflet-map.vue';

import countryCoords from './country-coordinates.js';

export default {
    name: 'Admin Dashboard',
    components: { Card, Sparkline, VueBlocksTree, JVectorMap, LeafletMap, ActionsList },
    props: {
        modelValue: Object,
    },
    data() {
        return {
            someVariableUnderYourControl: 0,
            websshProxyUrl: '',
            treeData : {
            },
            clients: [
                /*{id:'#-----', name:'-----', address:'---.---.---.---', loc:'-----', status:'--', stats:{cpu:.85,mem:.38,events:345,uptime:12421432}},*/
            ],
            clusters: [
                /*{id:'---', name:'-----', provider:'---', loc:'-----', status:'--', stats:{events:345,uptime:12421432}},*/
            ],
            geolocationCache: {
            },
            clientMarkers: [],
            clientConnections: [],
        };
    },
    watch: {
        modelValue: function() {
            this.initData();
        },
        treeData: function() {
            //console.log("TREE DATA UPDATED: ", this.treeData);
        }
    },
    computed: {
        treeData2: function() {
            return this.treeData;
        },
    },
    mounted() {
        this.initData();

        $('#ssh-console-dialog').dialog({
                autoOpen : false,
                modal : true,
                show : "blind",
                hide : "blind",
                minWidth: 800,
                minHeight: 600,
                buttons: {
                        Close: function() {
                                $('#ssh-console-dialog').dialog( "close" );
                        }
                },
                open: function() {
                        let websshProxy = $(this).attr('data-webssh-proxy');
                        let ref = $(this).attr('data-ref');
                        let address = $(this).attr('data-address');
                        console.log('SSH-console: open: websshProxy=', websshProxy, ', ref=', ref);
                        if (websshProxy.trim()==='' || ref.trim()==='') {
                                alert('ERROR: No webssh proxy or no client reference found');
                                return;
                        }

                        let url = `${websshProxy}?hostname=x&username=x&cref=${ref}`;
                        console.log('SSH-console: url: ', url);

                        let h = $('#ssh-console-dialog').css('height');
                        $('#ssh-console-dialog').html(`<object id="ssh-console-object" type="text/html" data="${url}" style="overflow: hidden; border: 5px ridge blue; width: 100% !important; height: ${h}px !important;" />`);
                        $('#ssh-console-dialog').dialog('option', "title", `SSH console: ${address}    [ref=${ref}]`);
                },
                close: function() {
                        $('#ssh-console-dialog').html('&nbsp;');
                        $('#ssh-console-dialog').dialog('option', "title", "SSH console");
                }
        });
    },

    methods: {
        refreshTree() {
            this.someVariableUnderYourControl = new Date().getTime();
        },
        emsServerLocation() {
            //return window.location.hostname;
            return this.modelValue['ip-address'];
        },
        getShorterNodeName(name) {
            let part = name.split(".", 2);
            if (part.length>1 && part[0]!=='' && isNaN(part[0])) {
                    return part[0];
            }
            return name;
        },
        getClusterStatus(nodeStatus) {
            return nodeStatus ? nodeStatus.toLowerCase().split(/[ _]/).map(word => word.charAt(0).toUpperCase() + word.substring(1)).join(' ') : '-';
        },
        getClusterStatusClass(nodeStatus) {
            switch (nodeStatus) {
                case 'AGGREGATOR': return 'badge-primary';
                case 'NOT_CANDIDATE': return 'badge-warning';
                case 'IGNORED': return 'badge-dark';
                default: return 'badge-secondary';
            }
        },

        openSshConsole(ref, address) {
            $('#ssh-console-dialog').attr("data-webssh-proxy", this.websshProxyUrl);
            $('#ssh-console-dialog').attr("data-ref", ref);
            $('#ssh-console-dialog').attr("data-address", address);
            $('#ssh-console-dialog').dialog("open");
        },
        electAggregator(zoneData) {
            let zoneId = zoneData.id;
            let command = 'CLUSTER-EXEC broker elect';
            let url = encodeURI('/cluster/command/'+zoneId+'/'+command);
            console.log('electAggregator: url='+url);
            this.callEmsServer(url);
        },
        appointAggregator(clientId, nodeId) {
            if (clientId[0]==='#') clientId = clientId.substr(1);
            let command = 'CLUSTER-EXEC broker appoint '+nodeId;
            let url = encodeURI('/client/command/'+clientId+'/'+command);
            console.log('appointAggregator: url='+url);
            this.callEmsServer(url);
        },
        callEmsServer(url) {
            $.get(url).then(
                    // success handler
                    function(data, textStatus, jqXHR) {
                            textStatus; jqXHR;
                            console.log('callEmsServer: OK: ', jqXHR.readyState, jqXHR.responseText, jqXHR.status, jqXHR.statusText);
                            console.log('callEmsServer: OK: ', data, textStatus, jqXHR);
                    },
                    // fail handler
                    function(jqXHR, textStatus, errorThrown) {
                            console.warn('callEmsServer: NOT_OK: url: ', url);
                            console.warn('callEmsServer: NOT_OK: ', jqXHR.readyState, jqXHR.responseText, jqXHR.status, jqXHR.statusText);
                            console.warn('callEmsServer: NOT_OK: ', jqXHR, 'textStatus=', textStatus, 'errorThrown=', errorThrown);
                    }
            )
            .always(function() {
                    console.log('callEmsServer: ALWAYS:');
            });
            console.log('callEmsServer: command sent: ', url);
        },

        // -------------------------------------------------------------------------------------------------------------

        initData() {
            if (!this.modelValue) return;

            if (this.modelValue['WEBSSH-BASE-URL'])
                this.websshProxyUrl = this.modelValue['WEBSSH-BASE-URL'];

            if (!this.modelValue['baguette-server'] || !this.modelValue['baguette-server']['active-clients-map'])
                return;

            let activeClients = this.modelValue['baguette-server']['active-clients-map'];
            let passiveClients = this.modelValue['baguette-server']['passive-clients-map'];
            let allClients = { ...passiveClients, ...activeClients };
            let _zones = { };
            let _clients = [ ];

            for (const id in allClients) {
                let clientData = allClients[id];
                if (!clientData.id) clientData.id = id;
                //console.log(id, ' ==> ', clientData);

                let clientObj = {
                    id: clientData['id'],
                    name: clientData['node-hostname'] ?? clientData['ip-address'],
                    nodeStatus: clientData['node-status'],
                    zone: clientData['node-zone'],
                    address: clientData['ip-address'],
                    port: clientData['node-port'],
                    grouping: clientData['grouping'],
                    reference: clientData['reference'],
                    nodeId: clientData['node-id'],
                    nodeState: clientData['node-state'],
                    loc: 'Unknown',
                    status: 'Up',
                    stats: {}
                };

                let zone = clientData['node-zone'];
                if (clientObj.nodeState==='IGNORE_NODE') zone = 'IGNORED';
                if (!zone) zone = '_default';
                if (!_zones[zone]) _zones[zone] = [ ];
                _zones[zone].push(clientObj);
                _clients.push(clientObj);
            }

            //console.log('_zones: ', _zones);
            //console.log('_clients: ', _clients);

            let _clusterData = [];
            for (let zid in _zones) {
                _clusterData.push({
                    id: zid,
                    name: zid,
                    provider: 'Unknown',
                    loc: 'Unknown',
                    status: 'Up',
                    stats: {},
                    aggregator: null
                });
            }
            //console.log('_clusterData: ', _clusterData);

            let _totalClusters = Object.keys(_zones).length;
            let _totalClients = 0;
            let _treeData = {
                label: 'EMS Server',
                expand: true,
                tree_node_type: 'ems',
                children: []
            };
            for (let zid in _zones) {
                let _zclients = _zones[zid];
                let _zdata = {
                    id: zid,
                    label: zid,
                    tree_node_type: 'zone',
                    expand: true,
                    children: []
                };
                _treeData.children.push(_zdata);
                for (let ii in _zclients) {
                    let _cdata = _zclients[ii];
                    _cdata.label = _cdata.name ?? 'Unknown';
                    _cdata.nodeStatus =
                            _cdata.nodeState==='IGNORE_NODE' ? 'IGNORED' :
                            _cdata.nodeState==='NOT_INSTALLED' ? 'NOT_CANDIDATE' :
                            _cdata.nodeStatus;
                    _cdata.tree_node_type =
                            _cdata.nodeState==='IGNORE_NODE' ? 'ignore' :
                            _cdata.nodeState==='NOT_INSTALLED' ? 'edge' :
                            'vm';
                    _zdata.children.push(_cdata);

                    if (_cdata.nodeStatus && _cdata.nodeStatus.toLowerCase()==='aggregator')
                        _zones[zid].aggregator = _cdata.id;
                }
                for (const cc of _zclients) {
                    cc.nextLevel = (cc.id==_zones[zid].aggregator) ? 'ems' : _zones[zid].aggregator;
                }
                _totalClients += _zdata.children.length;
                _zdata.totalClients = _zdata.children.length;
            }
            //console.log('_treeData: ', _treeData);
            _treeData.totalClusters = _totalClusters;
            _treeData.totalClients = _totalClients;

            this.clients = _clients;
            this.clusters = _clusterData;
            this.treeData = _treeData;

            // Add geolocation info for EMS server and Clients
            var emsObj = {
                    id: 'ems',
                    name: 'ems',
                    nodeStatus: 'ems',
                    address: this.modelValue['public-ip-address'],
                    reference: 'used-by-webssh',
                    loc: 'Unknown',
                    status: 'Up',
                    stats: {}
            };
            let clientsAndEms = [emsObj, ..._clients];
            this.addGeolocationInfo(clientsAndEms, this.geolocationCache);
            this.updateClientMarkers(clientsAndEms);
        },

        // -------------------------------------------------------------------------------------------------------------

        updateClientMarkers(clients) {
            let markers = [ ];
            for (let c of clients) {
                if (!c.lat || !c.lon) {
                    //console.warn('clientMarkers: Client without lat/lon: ', c);
                    continue;
                }
                let cc = {
                    latLng: [c.lat, c.lon],
                    name: this.getShorterNodeName(c.name),

                    // The next are used in LeafletMap component
                    id: c.id,
                    type: c.nodeStatus,
                    nextLevel: c.nextLevel,
                    popupText: `<b>${c.name}</b><br/>${c.nodeStatus}`,
                    tooltipText: this.getShorterNodeName(c.name),
                };
                markers.push(cc);
            }
            //console.log('NEW MARKERS: ', markers);
            this.clientMarkers = markers;

            // Update connections between EMS and clients
            let controlConns = this.clientMarkers.filter(cc => cc.id!=='ems' && cc.type!=='IGNORED' && cc.type!=='NOT_CANDIDATE')
                    .map(function(cc) {
                        return {
                            startMarker: 'ems',
                            endMarker: cc.id,
                            line: { color: '#696969', weight: 1, dashArray: '8 6' }
                        };
                    });
            let eventConns = this.clientMarkers.filter(cc => cc.id!=='ems')
                    .map(function(cc) {
                        return {
                            startMarker: cc.nextLevel,
                            endMarker: cc.id,
                            line: { color: cc.nextLevel=='ems' ? 'magenta' : 'blue', weight: 0.5 }
                        };
                    });
            this.clientConnections = controlConns.concat(eventConns);
        },

        addGeolocationInfo(dataArray, cache) {
            dataArray.forEach(c => {
                this.updateGeolocationInfoByIpAddress(c['address'], c, cache);
            });
        },
        updateGeolocationInfoByIpAddress(ipAddress, obj, cache) {
            // get cached info (if available)
            if (cache[ipAddress]) {
                this.updateGeolocationInfo(obj, cache[ipAddress]);
                return;
            }

            // call geolocation service
            const _updFunc = this.updateGeolocationInfo;
            $.getJSON("https://ipapi.co/"+ipAddress+"/json")
            .done(function(json) {
                console.log('AJAX: done: ', ipAddress, json);
                // cache geolocation info
                cache[ipAddress] = json;

                _updFunc(obj, json);
            })
            .fail(function(jqxhr, textStatus, error) {
                console.log('AJAX: fail: ', jqxhr, textStatus, error);
            })
            .always(function(/*jqxhr, textStatus*/) {
                //console.log('AJAX: always: ', jqxhr, textStatus);
            });
        },
        updateGeolocationInfo(obj, json) {
            // check if changed
            let emitEvent = true;
            /*if (obj.lat && obj.lon && obj.country) {
                emitEvent = ! (obj.lat==json.latitude && obj.lon==json.longitude && obj.country==json.country_name);
            }*/

            //console.log('updateGeolocationInfo: ', obj, json);
            obj.geo = json;
            obj.lat = json.latitude;
            obj.lon = json.longitude;
            obj.country = (json.org??'')+', '+(json.city??'')+', '+(json.region??'')+', '+(json.country_name??'-');

            /* // Pick a random country
            if (!json.country) {
                let codes = Object.keys(countryCoords);
                json.country = codes[ Math.floor(Math.random() * codes.length) ];
                console.log('RANDOM COUNTRY: ', json.country);
            } */

            // If country is unknown, set the location somewhere in the bottom of the map (i.e. somewhere in Antarctica)
            if (!json.country) {
                json.country = 'AQ';
                let cc = countryCoords[json.country];
                if (cc) {
                    json.latitude = cc.lat + (Math.floor(Math.random() * 10) - 5);
                    json.longitude = cc.lon + (Math.floor(Math.random() * 360) - 180);
                }
                console.log('UNKNOWN COUNTRY: Setting to \'Antarctica\' with random coordinates');
            }

            if (json.country && (!obj.lat || !obj.lon)) {
                let cc = countryCoords[json.country];
                //console.log('updateGeolocationInfo: Missing lat/lon in Geolocation response. Using country default lat/lon: ', obj, json, cc);
                if (cc) {
                    obj.lat = cc.lat;
                    obj.lon = cc.lon;
                }
            }

            if (emitEvent) {
                //XXX: TODO: Add emit update event in 'updateGeolocationInfoByIpAddress'
                //this.$emit('update:clientsValue', dataArray);
            }
        },
        clearGeolocationCache() {
            this.geolocationCache = {};
            this.clientMarkers = [];
            this.clientConnections = [];
            console.log('Geolocation cache purged');
        },
    }
}
</script>

<style scoped>
.tree-node-head {
    font-weight: bolder;
    margin: 0px;
    padding: 5px;
    width:100%;
}
</style>
