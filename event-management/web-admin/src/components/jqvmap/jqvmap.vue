<!--
    **  NOT WORKING  --  Fails to register 'vectorMap' function in '$.fn' **
-->
<!--
    See: https://www.10bestdesign.com/jqvmap/
         https://www.npmjs.com/search?q=jqvmap
         https://www.npmjs.com/package/jqvmap
         https://www.npmjs.com/package/jqvmap-novulnerability
         https://www.npmjs.com/package/jqvmap-noexploits
-->

<template>
    <div :id="id ?? jqvmid" :class="classes"></div>
</template>

<script>
var $ = require('jquery');
//require('jqvmap');
require('jqvmap');
require('jqvmap/dist/maps/jquery.vmap.world.js');

export default {
    name: 'jqvmap',
    props: {
        id: String,
        classes: String,
        width: { type:String, default:'100%' },
        height: { type:String, default:'500px' },
        options: Object,
        markers: Array,
    },
    data() {
        return {
            jqvmid: 'jqvmap_'+(Math.floor(Math.random()*1000000000))
        }
    },
    mounted() {
        let _id = this.id ?? this.jqvmid;
        let _conf = {
            map: 'world_en',
            backgroundColor: '#a5bfdd',
            borderColor: '#818181',
            borderOpacity: 0.25,
            borderWidth: 1,
            color: '#f4f3f0',
            enableZoom: true,
            hoverColor: '#c9dfaf',
            hoverOpacity: null,
            normalizeFunction: 'linear',
            scaleColors: ['#b6d6ff', '#005ace'],
            selectedColor: '#c9dfaf',
            selectedRegions: null,
            showTooltip: true,
            onRegionClick: function(element, code, region)
            {
                var message = 'You clicked "'
                    + region
                    + '" which has the code: '
                    + code.toUpperCase();
                console.log('JQVmap: ', message);
            }
        };
        _conf = Object.assign(_conf, this.options);
        $("#"+_id).empty().css('width', this.width).css('height', this.height);
        $("#"+_id).vectorMap(_conf);
    },
}
</script>