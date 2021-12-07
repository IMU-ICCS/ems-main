
export class Timeseries {
    #tsLength;
    #tsData;
    #seqLast;
    #allowUpdate = true;

    constructor(l) {
        if (!l || typeof l!=='number' || l<1) throw 'Timeseries.init(): Missing or invalid length argument';
        this.tsLength = l;
        this.tsData = new Array(l);
        this.tsData.fill(null);
        this.seqLast = undefined;
    }

    getLength() { return this.tsLength; }

    add(data, seq) {
        if (!data) return;
        if (!seq) {
            if (this.seqLast===undefined) seq = 0;
            else seq = this.seqLast + 1;
        }
        if (this.seqLast===undefined) {
            this.seqLast = seq;
            this.tsData.shift();
            this.tsData.push(data);
            return;
        }
        if (seq <= this.seqLast-this.tsLength)
            return;
        if (seq<=this.seqLast && this.allowUpdate) {
            let offset = this.tsLength - (this.seqLast-seq) - 1;
            this.tsData[offset] = data;
        }
        if (seq > this.seqLast) {
            let extra = seq - this.seqLast;
            for (let i=0; i<extra-1; i++) {
                this.tsData.shift();
                this.tsData.push(null);
            }
            this.tsData.shift();
            this.tsData.push(data);
            this.seqLast = seq;
        }
    }

    getData(l) {
         if (!l || typeof l!=='number' || l<1) throw 'Timeseries.getData(): Missing or invalid length argument';
         if (l>=this.tsLength) throw 'Timeseries.getData(): Length argument exceeds size: length='+l+', size='+this.tsLength;
         let from = this.tsLength - l;
         return this.tsData.slice(from);
    }

    getLast() {
        return (this.seqLast) ? this.tsData.at(-1) : null;
    }

    getDataWithSeq(l) {
        let s = this.seqLast - l + 1;
        let result = this.getData(l).map(data => ({data, seq: s++}));
        return result;
    }
}

export class TimeWindow extends Timeseries {
    #winLength;
    #winInterval;

    constructor(dur, ival) {
        let l = Math.floor(dur / ival) + 1;
        super(l);
        this.winLength = dur;
        this.winInterval = ival;
    }

    getWindowLength() { return this.winLength; }
    getWindowInterval() { return this.winInterval; }

    getWindowData(dur) {
        let l = Math.floor(dur / this.winInterval) + 1;
        return super.getData(l);
    }
}
