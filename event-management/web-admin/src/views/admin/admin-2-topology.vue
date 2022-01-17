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
                <LeafletMap :mapType="modelValue['LEAFLET_MAP_TYPE'] ?? 'openstreetmap'"
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

if (!json.country) {
    let codes = Object.keys(countryCoords);
    json.country = codes[ Math.floor(Math.random() * codes.length) ];
    console.log('RANDOM COUNTRY: ', json.country);
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
    }
}

const countryCoords = {
    'AD': { code: 'AD', lat: 42.546245, lon: 1.601554, country: 'Andorra' },
    'AE': { code: 'AE', lat: 23.424076, lon: 53.847818, country: 'United Arab Emirates' },
    'AF': { code: 'AF', lat: 33.93911, lon: 67.709953, country: 'Afghanistan' },
    'AG': { code: 'AG', lat: 17.060816, lon: -61.796428, country: 'Antigua and Barbuda' },
    'AI': { code: 'AI', lat: 18.220554, lon: -63.068615, country: 'Anguilla' },
    'AL': { code: 'AL', lat: 41.153332, lon: 20.168331, country: 'Albania' },
    'AM': { code: 'AM', lat: 40.069099, lon: 45.038189, country: 'Armenia' },
    'AN': { code: 'AN', lat: 12.226079, lon: -69.060087, country: 'Netherlands Antilles' },
    'AO': { code: 'AO', lat: -11.202692, lon: 17.873887, country: 'Angola' },
    'AQ': { code: 'AQ', lat: -75.250973, lon: -0.071389, country: 'Antarctica' },
    'AR': { code: 'AR', lat: -38.416097, lon: -63.616672, country: 'Argentina' },
    'AS': { code: 'AS', lat: -14.270972, lon: -170.132217, country: 'American Samoa' },
    'AT': { code: 'AT', lat: 47.516231, lon: 14.550072, country: 'Austria' },
    'AU': { code: 'AU', lat: -25.274398, lon: 133.775136, country: 'Australia' },
    'AW': { code: 'AW', lat: 12.52111, lon: -69.968338, country: 'Aruba' },
    'AZ': { code: 'AZ', lat: 40.143105, lon: 47.576927, country: 'Azerbaijan' },
    'BA': { code: 'BA', lat: 43.915886, lon: 17.679076, country: 'Bosnia and Herzegovina' },
    'BB': { code: 'BB', lat: 13.193887, lon: -59.543198, country: 'Barbados' },
    'BD': { code: 'BD', lat: 23.684994, lon: 90.356331, country: 'Bangladesh' },
    'BE': { code: 'BE', lat: 50.503887, lon: 4.469936, country: 'Belgium' },
    'BF': { code: 'BF', lat: 12.238333, lon: -1.561593, country: 'Burkina Faso' },
    'BG': { code: 'BG', lat: 42.733883, lon: 25.48583, country: 'Bulgaria' },
    'BH': { code: 'BH', lat: 25.930414, lon: 50.637772, country: 'Bahrain' },
    'BI': { code: 'BI', lat: -3.373056, lon: 29.918886, country: 'Burundi' },
    'BJ': { code: 'BJ', lat: 9.30769, lon: 2.315834, country: 'Benin' },
    'BM': { code: 'BM', lat: 32.321384, lon: -64.75737, country: 'Bermuda' },
    'BN': { code: 'BN', lat: 4.535277, lon: 114.727669, country: 'Brunei' },
    'BO': { code: 'BO', lat: -16.290154, lon: -63.588653, country: 'Bolivia' },
    'BR': { code: 'BR', lat: -14.235004, lon: -51.92528, country: 'Brazil' },
    'BS': { code: 'BS', lat: 25.03428, lon: -77.39628, country: 'Bahamas' },
    'BT': { code: 'BT', lat: 27.514162, lon: 90.433601, country: 'Bhutan' },
    'BV': { code: 'BV', lat: -54.423199, lon: 3.413194, country: 'Bouvet Island' },
    'BW': { code: 'BW', lat: -22.328474, lon: 24.684866, country: 'Botswana' },
    'BY': { code: 'BY', lat: 53.709807, lon: 27.953389, country: 'Belarus' },
    'BZ': { code: 'BZ', lat: 17.189877, lon: -88.49765, country: 'Belize' },
    'CA': { code: 'CA', lat: 56.130366, lon: -106.346771, country: 'Canada' },
    'CC': { code: 'CC', lat: -12.164165, lon: 96.870956, country: 'Cocos [Keeling] Islands' },
    'CD': { code: 'CD', lat: -4.038333, lon: 21.758664, country: 'Congo [DRC]' },
    'CF': { code: 'CF', lat: 6.611111, lon: 20.939444, country: 'Central African Republic' },
    'CG': { code: 'CG', lat: -0.228021, lon: 15.827659, country: 'Congo [Republic]' },
    'CH': { code: 'CH', lat: 46.818188, lon: 8.227512, country: 'Switzerland' },
    'CI': { code: 'CI', lat: 7.539989, lon: -5.54708, country: 'Côte d\'Ivoire' },
    'CK': { code: 'CK', lat: -21.236736, lon: -159.777671, country: 'Cook Islands' },
    'CL': { code: 'CL', lat: -35.675147, lon: -71.542969, country: 'Chile' },
    'CM': { code: 'CM', lat: 7.369722, lon: 12.354722, country: 'Cameroon' },
    'CN': { code: 'CN', lat: 35.86166, lon: 104.195397, country: 'China' },
    'CO': { code: 'CO', lat: 4.570868, lon: -74.297333, country: 'Colombia' },
    'CR': { code: 'CR', lat: 9.748917, lon: -83.753428, country: 'Costa Rica' },
    'CU': { code: 'CU', lat: 21.521757, lon: -77.781167, country: 'Cuba' },
    'CV': { code: 'CV', lat: 16.002082, lon: -24.013197, country: 'Cape Verde' },
    'CX': { code: 'CX', lat: -10.447525, lon: 105.690449, country: 'Christmas Island' },
    'CY': { code: 'CY', lat: 35.126413, lon: 33.429859, country: 'Cyprus' },
    'CZ': { code: 'CZ', lat: 49.817492, lon: 15.472962, country: 'Czech Republic' },
    'DE': { code: 'DE', lat: 51.165691, lon: 10.451526, country: 'Germany' },
    'DJ': { code: 'DJ', lat: 11.825138, lon: 42.590275, country: 'Djibouti' },
    'DK': { code: 'DK', lat: 56.26392, lon: 9.501785, country: 'Denmark' },
    'DM': { code: 'DM', lat: 15.414999, lon: -61.370976, country: 'Dominica' },
    'DO': { code: 'DO', lat: 18.735693, lon: -70.162651, country: 'Dominican Republic' },
    'DZ': { code: 'DZ', lat: 28.033886, lon: 1.659626, country: 'Algeria' },
    'EC': { code: 'EC', lat: -1.831239, lon: -78.183406, country: 'Ecuador' },
    'EE': { code: 'EE', lat: 58.595272, lon: 25.013607, country: 'Estonia' },
    'EG': { code: 'EG', lat: 26.820553, lon: 30.802498, country: 'Egypt' },
    'EH': { code: 'EH', lat: 24.215527, lon: -12.885834, country: 'Western Sahara' },
    'ER': { code: 'ER', lat: 15.179384, lon: 39.782334, country: 'Eritrea' },
    'ES': { code: 'ES', lat: 40.463667, lon: -3.74922, country: 'Spain' },
    'ET': { code: 'ET', lat: 9.145, lon: 40.489673, country: 'Ethiopia' },
    'FI': { code: 'FI', lat: 61.92411, lon: 25.748151, country: 'Finland' },
    'FJ': { code: 'FJ', lat: -16.578193, lon: 179.414413, country: 'Fiji' },
    'FK': { code: 'FK', lat: -51.796253, lon: -59.523613, country: 'Falkland Islands [Islas Malvinas]' },
    'FM': { code: 'FM', lat: 7.425554, lon: 150.550812, country: 'Micronesia' },
    'FO': { code: 'FO', lat: 61.892635, lon: -6.911806, country: 'Faroe Islands' },
    'FR': { code: 'FR', lat: 46.227638, lon: 2.213749, country: 'France' },
    'GA': { code: 'GA', lat: -0.803689, lon: 11.609444, country: 'Gabon' },
    'GB': { code: 'GB', lat: 55.378051, lon: -3.435973, country: 'United Kingdom' },
    'GD': { code: 'GD', lat: 12.262776, lon: -61.604171, country: 'Grenada' },
    'GE': { code: 'GE', lat: 42.315407, lon: 43.356892, country: 'Georgia' },
    'GF': { code: 'GF', lat: 3.933889, lon: -53.125782, country: 'French Guiana' },
    'GG': { code: 'GG', lat: 49.465691, lon: -2.585278, country: 'Guernsey' },
    'GH': { code: 'GH', lat: 7.946527, lon: -1.023194, country: 'Ghana' },
    'GI': { code: 'GI', lat: 36.137741, lon: -5.345374, country: 'Gibraltar' },
    'GL': { code: 'GL', lat: 71.706936, lon: -42.604303, country: 'Greenland' },
    'GM': { code: 'GM', lat: 13.443182, lon: -15.310139, country: 'Gambia' },
    'GN': { code: 'GN', lat: 9.945587, lon: -9.696645, country: 'Guinea' },
    'GP': { code: 'GP', lat: 16.995971, lon: -62.067641, country: 'Guadeloupe' },
    'GQ': { code: 'GQ', lat: 1.650801, lon: 10.267895, country: 'Equatorial Guinea' },
    'GR': { code: 'GR', lat: 39.074208, lon: 21.824312, country: 'Greece' },
    'GS': { code: 'GS', lat: -54.429579, lon: -36.587909, country: 'South Georgia and the South Sandwich Islands' },
    'GT': { code: 'GT', lat: 15.783471, lon: -90.230759, country: 'Guatemala' },
    'GU': { code: 'GU', lat: 13.444304, lon: 144.793731, country: 'Guam' },
    'GW': { code: 'GW', lat: 11.803749, lon: -15.180413, country: 'Guinea-Bissau' },
    'GY': { code: 'GY', lat: 4.860416, lon: -58.93018, country: 'Guyana' },
    'GZ': { code: 'GZ', lat: 31.354676, lon: 34.308825, country: 'Gaza Strip' },
    'HK': { code: 'HK', lat: 22.396428, lon: 114.109497, country: 'Hong Kong' },
    'HM': { code: 'HM', lat: -53.08181, lon: 73.504158, country: 'Heard Island and McDonald Islands' },
    'HN': { code: 'HN', lat: 15.199999, lon: -86.241905, country: 'Honduras' },
    'HR': { code: 'HR', lat: 45.1, lon: 15.2, country: 'Croatia' },
    'HT': { code: 'HT', lat: 18.971187, lon: -72.285215, country: 'Haiti' },
    'HU': { code: 'HU', lat: 47.162494, lon: 19.503304, country: 'Hungary' },
    'ID': { code: 'ID', lat: -0.789275, lon: 113.921327, country: 'Indonesia' },
    'IE': { code: 'IE', lat: 53.41291, lon: -8.24389, country: 'Ireland' },
    'IL': { code: 'IL', lat: 31.046051, lon: 34.851612, country: 'Israel' },
    'IM': { code: 'IM', lat: 54.236107, lon: -4.548056, country: 'Isle of Man' },
    'IN': { code: 'IN', lat: 20.593684, lon: 78.96288, country: 'India' },
    'IO': { code: 'IO', lat: -6.343194, lon: 71.876519, country: 'British Indian Ocean Territory' },
    'IQ': { code: 'IQ', lat: 33.223191, lon: 43.679291, country: 'Iraq' },
    'IR': { code: 'IR', lat: 32.427908, lon: 53.688046, country: 'Iran' },
    'IS': { code: 'IS', lat: 64.963051, lon: -19.020835, country: 'Iceland' },
    'IT': { code: 'IT', lat: 41.87194, lon: 12.56738, country: 'Italy' },
    'JE': { code: 'JE', lat: 49.214439, lon: -2.13125, country: 'Jersey' },
    'JM': { code: 'JM', lat: 18.109581, lon: -77.297508, country: 'Jamaica' },
    'JO': { code: 'JO', lat: 30.585164, lon: 36.238414, country: 'Jordan' },
    'JP': { code: 'JP', lat: 36.204824, lon: 138.252924, country: 'Japan' },
    'KE': { code: 'KE', lat: -0.023559, lon: 37.906193, country: 'Kenya' },
    'KG': { code: 'KG', lat: 41.20438, lon: 74.766098, country: 'Kyrgyzstan' },
    'KH': { code: 'KH', lat: 12.565679, lon: 104.990963, country: 'Cambodia' },
    'KI': { code: 'KI', lat: -3.370417, lon: -168.734039, country: 'Kiribati' },
    'KM': { code: 'KM', lat: -11.875001, lon: 43.872219, country: 'Comoros' },
    'KN': { code: 'KN', lat: 17.357822, lon: -62.782998, country: 'Saint Kitts and Nevis' },
    'KP': { code: 'KP', lat: 40.339852, lon: 127.510093, country: 'North Korea' },
    'KR': { code: 'KR', lat: 35.907757, lon: 127.766922, country: 'South Korea' },
    'KW': { code: 'KW', lat: 29.31166, lon: 47.481766, country: 'Kuwait' },
    'KY': { code: 'KY', lat: 19.513469, lon: -80.566956, country: 'Cayman Islands' },
    'KZ': { code: 'KZ', lat: 48.019573, lon: 66.923684, country: 'Kazakhstan' },
    'LA': { code: 'LA', lat: 19.85627, lon: 102.495496, country: 'Laos' },
    'LB': { code: 'LB', lat: 33.854721, lon: 35.862285, country: 'Lebanon' },
    'LC': { code: 'LC', lat: 13.909444, lon: -60.978893, country: 'Saint Lucia' },
    'LI': { code: 'LI', lat: 47.166, lon: 9.555373, country: 'Liechtenstein' },
    'LK': { code: 'LK', lat: 7.873054, lon: 80.771797, country: 'Sri Lanka' },
    'LR': { code: 'LR', lat: 6.428055, lon: -9.429499, country: 'Liberia' },
    'LS': { code: 'LS', lat: -29.609988, lon: 28.233608, country: 'Lesotho' },
    'LT': { code: 'LT', lat: 55.169438, lon: 23.881275, country: 'Lithuania' },
    'LU': { code: 'LU', lat: 49.815273, lon: 6.129583, country: 'Luxembourg' },
    'LV': { code: 'LV', lat: 56.879635, lon: 24.603189, country: 'Latvia' },
    'LY': { code: 'LY', lat: 26.3351, lon: 17.228331, country: 'Libya' },
    'MA': { code: 'MA', lat: 31.791702, lon: -7.09262, country: 'Morocco' },
    'MC': { code: 'MC', lat: 43.750298, lon: 7.412841, country: 'Monaco' },
    'MD': { code: 'MD', lat: 47.411631, lon: 28.369885, country: 'Moldova' },
    'ME': { code: 'ME', lat: 42.708678, lon: 19.37439, country: 'Montenegro' },
    'MG': { code: 'MG', lat: -18.766947, lon: 46.869107, country: 'Madagascar' },
    'MH': { code: 'MH', lat: 7.131474, lon: 171.184478, country: 'Marshall Islands' },
    'MK': { code: 'MK', lat: 41.608635, lon: 21.745275, country: 'Macedonia [FYROM]' },
    'ML': { code: 'ML', lat: 17.570692, lon: -3.996166, country: 'Mali' },
    'MM': { code: 'MM', lat: 21.913965, lon: 95.956223, country: 'Myanmar [Burma]' },
    'MN': { code: 'MN', lat: 46.862496, lon: 103.846656, country: 'Mongolia' },
    'MO': { code: 'MO', lat: 22.198745, lon: 113.543873, country: 'Macau' },
    'MP': { code: 'MP', lat: 17.33083, lon: 145.38469, country: 'Northern Mariana Islands' },
    'MQ': { code: 'MQ', lat: 14.641528, lon: -61.024174, country: 'Martinique' },
    'MR': { code: 'MR', lat: 21.00789, lon: -10.940835, country: 'Mauritania' },
    'MS': { code: 'MS', lat: 16.742498, lon: -62.187366, country: 'Montserrat' },
    'MT': { code: 'MT', lat: 35.937496, lon: 14.375416, country: 'Malta' },
    'MU': { code: 'MU', lat: -20.348404, lon: 57.552152, country: 'Mauritius' },
    'MV': { code: 'MV', lat: 3.202778, lon: 73.22068, country: 'Maldives' },
    'MW': { code: 'MW', lat: -13.254308, lon: 34.301525, country: 'Malawi' },
    'MX': { code: 'MX', lat: 23.634501, lon: -102.552784, country: 'Mexico' },
    'MY': { code: 'MY', lat: 4.210484, lon: 101.975766, country: 'Malaysia' },
    'MZ': { code: 'MZ', lat: -18.665695, lon: 35.529562, country: 'Mozambique' },
    'NA': { code: 'NA', lat: -22.95764, lon: 18.49041, country: 'Namibia' },
    'NC': { code: 'NC', lat: -20.904305, lon: 165.618042, country: 'New Caledonia' },
    'NE': { code: 'NE', lat: 17.607789, lon: 8.081666, country: 'Niger' },
    'NF': { code: 'NF', lat: -29.040835, lon: 167.954712, country: 'Norfolk Island' },
    'NG': { code: 'NG', lat: 9.081999, lon: 8.675277, country: 'Nigeria' },
    'NI': { code: 'NI', lat: 12.865416, lon: -85.207229, country: 'Nicaragua' },
    'NL': { code: 'NL', lat: 52.132633, lon: 5.291266, country: 'Netherlands' },
    'NO': { code: 'NO', lat: 60.472024, lon: 8.468946, country: 'Norway' },
    'NP': { code: 'NP', lat: 28.394857, lon: 84.124008, country: 'Nepal' },
    'NR': { code: 'NR', lat: -0.522778, lon: 166.931503, country: 'Nauru' },
    'NU': { code: 'NU', lat: -19.054445, lon: -169.867233, country: 'Niue' },
    'NZ': { code: 'NZ', lat: -40.900557, lon: 174.885971, country: 'New Zealand' },
    'OM': { code: 'OM', lat: 21.512583, lon: 55.923255, country: 'Oman' },
    'PA': { code: 'PA', lat: 8.537981, lon: -80.782127, country: 'Panama' },
    'PE': { code: 'PE', lat: -9.189967, lon: -75.015152, country: 'Peru' },
    'PF': { code: 'PF', lat: -17.679742, lon: -149.406843, country: 'French Polynesia' },
    'PG': { code: 'PG', lat: -6.314993, lon: 143.95555, country: 'Papua New Guinea' },
    'PH': { code: 'PH', lat: 12.879721, lon: 121.774017, country: 'Philippines' },
    'PK': { code: 'PK', lat: 30.375321, lon: 69.345116, country: 'Pakistan' },
    'PL': { code: 'PL', lat: 51.919438, lon: 19.145136, country: 'Poland' },
    'PM': { code: 'PM', lat: 46.941936, lon: -56.27111, country: 'Saint Pierre and Miquelon' },
    'PN': { code: 'PN', lat: -24.703615, lon: -127.439308, country: 'Pitcairn Islands' },
    'PR': { code: 'PR', lat: 18.220833, lon: -66.590149, country: 'Puerto Rico' },
    'PS': { code: 'PS', lat: 31.952162, lon: 35.233154, country: 'Palestinian Territories' },
    'PT': { code: 'PT', lat: 39.399872, lon: -8.224454, country: 'Portugal' },
    'PW': { code: 'PW', lat: 7.51498, lon: 134.58252, country: 'Palau' },
    'PY': { code: 'PY', lat: -23.442503, lon: -58.443832, country: 'Paraguay' },
    'QA': { code: 'QA', lat: 25.354826, lon: 51.183884, country: 'Qatar' },
    'RE': { code: 'RE', lat: -21.115141, lon: 55.536384, country: 'Réunion' },
    'RO': { code: 'RO', lat: 45.943161, lon: 24.96676, country: 'Romania' },
    'RS': { code: 'RS', lat: 44.016521, lon: 21.005859, country: 'Serbia' },
    'RU': { code: 'RU', lat: 61.52401, lon: 105.318756, country: 'Russia' },
    'RW': { code: 'RW', lat: -1.940278, lon: 29.873888, country: 'Rwanda' },
    'SA': { code: 'SA', lat: 23.885942, lon: 45.079162, country: 'Saudi Arabia' },
    'SB': { code: 'SB', lat: -9.64571, lon: 160.156194, country: 'Solomon Islands' },
    'SC': { code: 'SC', lat: -4.679574, lon: 55.491977, country: 'Seychelles' },
    'SD': { code: 'SD', lat: 12.862807, lon: 30.217636, country: 'Sudan' },
    'SE': { code: 'SE', lat: 60.128161, lon: 18.643501, country: 'Sweden' },
    'SG': { code: 'SG', lat: 1.352083, lon: 103.819836, country: 'Singapore' },
    'SH': { code: 'SH', lat: -24.143474, lon: -10.030696, country: 'Saint Helena' },
    'SI': { code: 'SI', lat: 46.151241, lon: 14.995463, country: 'Slovenia' },
    'SJ': { code: 'SJ', lat: 77.553604, lon: 23.670272, country: 'Svalbard and Jan Mayen' },
    'SK': { code: 'SK', lat: 48.669026, lon: 19.699024, country: 'Slovakia' },
    'SL': { code: 'SL', lat: 8.460555, lon: -11.779889, country: 'Sierra Leone' },
    'SM': { code: 'SM', lat: 43.94236, lon: 12.457777, country: 'San Marino' },
    'SN': { code: 'SN', lat: 14.497401, lon: -14.452362, country: 'Senegal' },
    'SO': { code: 'SO', lat: 5.152149, lon: 46.199616, country: 'Somalia' },
    'SR': { code: 'SR', lat: 3.919305, lon: -56.027783, country: 'Suriname' },
    'ST': { code: 'ST', lat: 0.18636, lon: 6.613081, country: 'São Tomé and Príncipe' },
    'SV': { code: 'SV', lat: 13.794185, lon: -88.89653, country: 'El Salvador' },
    'SY': { code: 'SY', lat: 34.802075, lon: 38.996815, country: 'Syria' },
    'SZ': { code: 'SZ', lat: -26.522503, lon: 31.465866, country: 'Swaziland' },
    'TC': { code: 'TC', lat: 21.694025, lon: -71.797928, country: 'Turks and Caicos Islands' },
    'TD': { code: 'TD', lat: 15.454166, lon: 18.732207, country: 'Chad' },
    'TF': { code: 'TF', lat: -49.280366, lon: 69.348557, country: 'French Southern Territories' },
    'TG': { code: 'TG', lat: 8.619543, lon: 0.824782, country: 'Togo' },
    'TH': { code: 'TH', lat: 15.870032, lon: 100.992541, country: 'Thailand' },
    'TJ': { code: 'TJ', lat: 38.861034, lon: 71.276093, country: 'Tajikistan' },
    'TK': { code: 'TK', lat: -8.967363, lon: -171.855881, country: 'Tokelau' },
    'TL': { code: 'TL', lat: -8.874217, lon: 125.727539, country: 'Timor-Leste' },
    'TM': { code: 'TM', lat: 38.969719, lon: 59.556278, country: 'Turkmenistan' },
    'TN': { code: 'TN', lat: 33.886917, lon: 9.537499, country: 'Tunisia' },
    'TO': { code: 'TO', lat: -21.178986, lon: -175.198242, country: 'Tonga' },
    'TR': { code: 'TR', lat: 38.963745, lon: 35.243322, country: 'Turkey' },
    'TT': { code: 'TT', lat: 10.691803, lon: -61.222503, country: 'Trinidad and Tobago' },
    'TV': { code: 'TV', lat: -7.109535, lon: 177.64933, country: 'Tuvalu' },
    'TW': { code: 'TW', lat: 23.69781, lon: 120.960515, country: 'Taiwan' },
    'TZ': { code: 'TZ', lat: -6.369028, lon: 34.888822, country: 'Tanzania' },
    'UA': { code: 'UA', lat: 48.379433, lon: 31.16558, country: 'Ukraine' },
    'UG': { code: 'UG', lat: 1.373333, lon: 32.290275, country: 'Uganda' },
    'UM': { code: 'UM', lat: 0, lon: 0, country: 'U.S. Minor Outlying Islands' },
    'US': { code: 'US', lat: 37.09024, lon: -95.712891, country: 'United States' },
    'UY': { code: 'UY', lat: -32.522779, lon: -55.765835, country: 'Uruguay' },
    'UZ': { code: 'UZ', lat: 41.377491, lon: 64.585262, country: 'Uzbekistan' },
    'VA': { code: 'VA', lat: 41.902916, lon: 12.453389, country: 'Vatican City' },
    'VC': { code: 'VC', lat: 12.984305, lon: -61.287228, country: 'Saint Vincent and the Grenadines' },
    'VE': { code: 'VE', lat: 6.42375, lon: -66.58973, country: 'Venezuela' },
    'VG': { code: 'VG', lat: 18.420695, lon: -64.639968, country: 'British Virgin Islands' },
    'VI': { code: 'VI', lat: 18.335765, lon: -64.896335, country: 'U.S. Virgin Islands' },
    'VN': { code: 'VN', lat: 14.058324, lon: 108.277199, country: 'Vietnam' },
    'VU': { code: 'VU', lat: -15.376706, lon: 166.959158, country: 'Vanuatu' },
    'WF': { code: 'WF', lat: -13.768752, lon: -177.156097, country: 'Wallis and Futuna' },
    'WS': { code: 'WS', lat: -13.759029, lon: -172.104629, country: 'Samoa' },
    'XK': { code: 'XK', lat: 42.602636, lon: 20.902977, country: 'Kosovo' },
    'YE': { code: 'YE', lat: 15.552727, lon: 48.516388, country: 'Yemen' },
    'YT': { code: 'YT', lat: -12.8275, lon: 45.166244, country: 'Mayotte' },
    'ZA': { code: 'ZA', lat: -30.559482, lon: 22.937506, country: 'South Africa' },
    'ZM': { code: 'ZM', lat: -13.133897, lon: 27.849332, country: 'Zambia' },
    'ZW': { code: 'ZW', lat: -19.015438, lon: 29.154857, country: 'Zimbabwe' },
};
</script>

<style scoped>
.tree-node-head {
    font-weight: bolder;
    margin: 0px;
    padding: 5px;
    width:100%;
}
</style>
