<template>
  <div id="calendar-wrap">
    <Calendar
      v-if="setMArkers"
      ref="calendar"
      :sundayBegin="props.sundayBegin"
      :format="props.format.options[0]"
      :markers="props.markers"
      :disabledFutureDay="props.disabledFutureDay"
      :hideOtherMonthDay="props.hideOtherMonthDay"
      :hideOtherMonthMarker="props.hideOtherMonthMarker"
      @date="handleDateChange($event)"
    ></Calendar>
  </div>
</template>

<script>

import Calendar from "./CalendarShowContent";
import axios from 'axios';

const date = new Date();
const year = date.getFullYear();
const month = date.getMonth() + 1;

export default {
  name: "CalendarShow",
  components: {
    Calendar
  },
  data() {
    return {
      activities: [],
      props: {
        disabledFutureDay: false,
        hideOtherMonthDay: false,
        hideOtherMonthMarker: true,
        sundayBegin: false,
        format: {
          selected: "YYYY/MM/DD",
          options: ["YYYY/MM/DD", "YYYY-M-D", "YY/MM/DD", "YY/M/D"]
        },
        weekText: {
          lang: "EN",
          selected: "",
          options: [
            {
              lang: "EN",
              value: ["M", "T", "W", "T", "F", "S", "S"]
            },
            {
              lang: "ZH",
              value: ["一", "二", "三", "四", "五", "六", "日"]
            }
          ]
        },
        markers: []
      }
    };
  },
  mounted() {
    //this.$refs.calendar.chooseTargetDate("2019/04/14");
  },
  computed: {
    setMArkers(){
        let activities = this.$store.getters.getActivities;
        for(var i = 0; i < activities.length; i++){
          if(activities[i].recurrence_id != null){
            this.props.markers.push({date: activities[i].activityDate, className: "activityRecurrent", title: activities[i].title,  hour: activities[i].hour})
          }else{
            this.props.markers.push({date: activities[i].activityDate, className: "activitySimple", title: activities[i].title,  hour: activities[i].hour})
          }
        }
        if(this.props.markers.length > 0){
              console.log(this.props.markers);
            return true;
        }
    }
  },
  methods: {
    handleDateChange(obj) {
      console.log(JSON.stringify(obj));
    },
    
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('login'); 
    }
  }
};
</script>

<style>
#calendar-wrap {
  padding-bottom: 30px;
  color: #2c3e50;
}
/* marker */
#calendar-wrap  #calendar {
  max-width: 414px;
  margin: auto;
}
/* marker style*/
#calendar-wrap .love span {
  color: #fff;
  background-color: #7b1fa2;
}
#calendar-wrap .miss span {
  color: #fff;
  background-color: #ff4081;
}

#calendar-wrap .activitySimple span {
  color: #fff;
  background-color: #448aff;
}
#calendar-wrap .activityRecurrent span {
  color: #fff;
  background-color: #42ebf4;
}
#calendar-wrap .today span {
  color: #fff;
  background-color: #ff5722;
}
.today span:before{
  content: "Today";
  font-size: 9px;
  position: absolute;
  top: -25px;
  color: #000;
  right: 0;
  left: 0;
}
#calendar-wrap span .title, #calendar-wrap span .hour{
  position: absolute;
    top: -17px;
    left: 25px;
    z-index: 9999;
    background: gray;
    padding: 0 5px;
    font-weight: 300;
    font-size: 10px;
    line-height: 24px;
    height: auto;
    width: auto;
    color: #fff !important;
    display: none;
    border-radius: 30px;
}
#calendar-wrap span .hour{
  left: -25px;
}
#calendar-wrap span:hover > .title, #calendar-wrap span:hover > .hour{
  display: inline-block;
}

#calendar-wrap .weekend-day span {
  color: #536dfe;
}

#calendar-wrap span{
  position: relative;
}

</style>
