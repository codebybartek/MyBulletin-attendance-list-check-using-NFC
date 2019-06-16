<template>
  <div class="container offset_top">
    <div class="row">
      <div class="activitiesCalendar col-12 col-lg-6">
          <CalendarShow v-if="activities.length != 0" />
       </div>
       <div class="right_column col-12 col-lg-6">
         <h1 @click="setDates()">Add your activity</h1>
         <span class="success">{{success}}</span>      
         <section class="navigation_buttons">
           <h3 class="nav-button" @click="showRecurent = !showRecurent; showSimple = false">{{ calendars[1].title }}</h3>
           <h3 class="nav-button" @click="showSimple = !showSimple; showRecurent = false">{{ calendars[0].title }}</h3>
         </section>
        <div v-if="showSimple" class="activity simpleActivity col-12"> 
            <span class="label">Date</span>
            <Calendar       
            v-model="calendars[0].value"
            :range="calendars[0].range"
            :lang="calendars[0].lang"
            :firstDayOfWeek="calendars[0].firstDayOfWeek"
            :input-class="calendars[0].inputClass"
            :position="calendars[0].position"
            :disabled-start-date="calendars[0].disabledStartDate"
            :text-format="calendars[0].textFormat"
            :date-format="calendars[0].dateFormat"
            :disabled-end-date="calendars[0].disabledEndDate"/>
            <span class="label">Time & Duration(min)</span>
            <div class="datetime">
              <div class="time">
                <input type="number" placeholder="hh" v-model="hh" min="0" max="23" @change="evaluate({pointer: 'hh'})" />
                <input type="number" placeholder="mm" v-model="mm" min="0" max="59" step="30" @change="evaluate({pointer: 'mm'})" />
                <span v-show="notNull" @click="clear">&times;</span>
              </div>
              <div class="duration">
                <input type="number" placeholder="Duration(min)" v-model="activity.duration" min="0" max="600" step="15" @change="evaluate({pointer: 'hh'})" />
              </div>
            </div>
            <div class="content">
              <input v-model="activity.title" type="text" placeholder="Activity title" class="title"/>
              <select v-model="activity.course_id" class="courseId">    
                <option value="0" selected>Free activity</option>   
                <option v-for="course in courses" :value="course.id">
                  {{course.name}}
                </option>                                                   
              </select>
              <select v-if="activity.course_id == 0" v-model="activity.group_id" class="courseId">    
                <option value="0" disabled>Chose group</option>   
                <option v-for="group in groups" :value="group.id">
                  {{group.name}}
                </option>                                                   
              </select>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addActivity(false)" >Add activity</button>
            </div>
          </div>
          <div v-if="showRecurent" class="activity recurentActivity col-12"> 
            <span class="label">Date</span>
            <Calendar
            v-model="calendars[1].value"
            :range="calendars[1].range"
            :lang="calendars[1].lang"
            :firstDayOfWeek="calendars[1].firstDayOfWeek"
            :input-class="calendars[1].inputClass"
            :position="calendars[1].position"
            :disabled-start-date="calendars[1].disabledStartDate"
            :text-format="calendars[1].textFormat"
            :date-format="calendars[1].dateFormat"
            :disabled-end-date="calendars[1].disabledEndDate"/>
            <span class="label">Time & Duration(min)</span>
            <div class="datetime">
              <div class="time">
                <input type="number" placeholder="hh" v-model="hh" min="0" max="23" @change="evaluate({pointer: 'hh'})" />
                <input type="number" placeholder="mm" v-model="mm" min="0" max="59" step="30" @change="evaluate({pointer: 'mm'})" />
                <span v-show="notNull" @click="clear">&times;</span>
              </div>
              <div class="duration">
                <input type="number" placeholder="Duration(min)" v-model="activity.duration" min="0" max="600" step="15" @change="evaluate({pointer: 'hh'})" />
              </div>
            </div>
            <div class="content">
              <select v-model="recurrence" class="recurrence"> 
                <option value="0" selected>Every week</option>    
                <option value="1" selected>Every day</option> 
              </select>
              <input v-model="activity.title" type="text" placeholder="Activity title" class="title"/>
              <select v-model="activity.course_id" class="courseId">    
                <option value="0" selected>Free activity</option>   
                <option v-for="course in courses" :value="course.id">
                  {{course.name}}
                </option>                                                   
              </select>
               <select v-if="activity.course_id == 0" v-model="activity.group_id" class="courseId">    
                <option value="0" disabled>Chose group</option>   
                <option v-for="group in groups" :value="group.id">
                  {{group.name}}
                </option>                                                   
              </select>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addActivity(true)">Add activity</button>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <section class="col-12 activities_show">
          <h2>Activities</h2>
          <table class="table table-hover">
            <tr class="header_table">
                <th>Id</th>
                <th>Title</th>
                <th>Date</th>
                <th>Hour</th>
                <th>Duration</th>
                <th>Check activity</th>
            </tr>
            <tr v-for="activitySelected in activitiesSelected">
                <td>{{activitySelected.id}}</td>
                <td>{{activitySelected.title}}</td>
                <td>{{activitySelected.activityDate}}</td>
                <td>{{activitySelected.hour}}</td>
                <td>{{activitySelected.duration}}</td>
                <td><router-link :to="'/activities/' + activitySelected.id">more..</router-link></td>
            </tr>
          </table>
          {{takeActivitiesSelected}}
        </section>
      </div>
  </div>
</template>

<script>

import Calendar from './calendar.vue';
import CalendarShow from './CalendarShow.vue';
import axios from 'axios';

export default {
  name: 'addActivity',
  components: {
    Calendar,
    CalendarShow
  },
  data() {
    return {
      showSimple: false,
      showRecurent: false,
      newdate: null,
      courses: [],
      groups: [],
      dates: [],
      errors: [],
      success: "",
      activities: [],
      activitiesSelected: [],
      recurrence: 0,
      activity: {
        activityDate: "",
        title: '',
        duration: null,
        hour: null,
        course_id: 0,
        recurrence_id: null,
        professor_id: null,
        group_id: 0
      },
      calendars: [
        { title: 'Simple activity',
          inputClass: 'exampleDatePicker',
          lang: 'en',
          position: 'left',
          range: false,
          value: new Date(),
          firstDayOfWeek: 'monday'
        },
        { title: 'Recurrent activity',
          inputClass: 'exampleDatePicker',
          lang: 'en',
          position: 'right',
          range: true,
          value: [new Date(), new Date(new Date().getTime() + ( 9 * 24 * 60 * 60 * 1000))]
        }
      ],
      hh: null,
      mm: null,

    }
  },
  computed: {
    takeDate() {
      return this.newdate;
    },
    notNull(){
      return (this.hh !== null || this.mm !== null);
    },
    takeActivitiesSelected() {
      if(this.activitiesSelected.length != 0){
        this.activitiesSelected = [];
      }
      if(this.activities.length != 0){
        let selectedDate = this.$store.getters.getSelectedDate.replace("/", "-");
        selectedDate = selectedDate.replace("/", "-");
        for(var i = 0; i < this.activities.length; i++){
          if(this.activities[i].activityDate === selectedDate){
            this.activitiesSelected.push(this.activities[i]);
          }
          console.log(selectedDate + "------" + this.activities[i].activityDate);
        }
      }
      
    }
  },
  methods: {
     addActivity(isRecurrent) {
      this.errors = [];
      if(!this.activity.title){
        this.errors.push("Activity title is required");
        return;
      }
      if(this.activity.duration===0){
        this.errors.push("Duration is required");
        return;
      }
       if(!this.hh || !this.mm){
        this.errors.push("Time is required");
        return;
      }
      if(this.activity.course_id === 0){
        this.activity.course_id = null;
      }
      if(this.activity.group_id === 0){
        this.activity.group_id = null;
      }

      this.activity.hour = this.hh + ":" + this.mm;

      if(isRecurrent){
        this.setDates();
        let length = this.dates.length;
        let recurrence = [{dateStart: this.dates[0], dateEnd: this.dates[length-1], everyDay: 0}];
        axios.post(this.$store.getters.getUrl + 'api/recurrences', recurrence[0]).then((response) =>{
          this.success = response.data['created'];
          this.activity.recurrence_id = response.data['id'];

        for(var i = 0; i < length; i++){
          let activityTemp = {activityDate: this.dates[i], title: this.activity.title, duration: this.activity.duration, hour:  this.activity.hour, course_id: this.activity.course_id, recurrence_id:  this.activity.recurrence_id, checked: false, group_id: this.activity.group_id, professor_id: localStorage.getItem('professor_id')};
          axios.post(this.$store.getters.getUrl + 'api/activities', activityTemp).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));
        }

        })
        .catch(function(error){
          this.errors.push("Something went wrong");
        }.bind(this));
      }else{
        let newdate = this.calendars[0].value;
        this.activity.activityDate = (newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate());

        this.activity.professor_id = localStorage.getItem('professor_id');

        this.activity.checked = false;
        axios.post(this.$store.getters.getUrl + 'api/activities', this.activity).then((response) =>{
          this.success = response.data['created'];
        })
        .catch(function(error){
          this.errors.push("Something went wrong");
        }.bind(this));
      }
    },
    setDates() {
     let newdate = this.calendars[1].value[0];
     this.dates.push(newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate());
     let rec;
     if(this.recurrence === 0){
        rec = 7;
     }else{
        rec = 1;
     }
     while(1){
      newdate.setDate(newdate.getDate() + rec);
      if(newdate > this.calendars[1].value[1]){
        break;
      }
      this.dates.push(newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate());
     }
    },
    clear() {
       this.hh = this.mm = null;
    },
    evaluate(settings) {
      // Using the pointer to avoid having a method for each input
      var value = this[settings.pointer];
      if(!isNaN(settings.max) && this[settings.pointer] > settings.max)
      {
        value =  settings.max;
      }
      if(!isNaN(settings.min) && this[settings.pointer] < settings.min)
      {
        value = settings.min;
      }
      this[settings.pointer] = value
      // Always good to emit changes, no?
      this.$emit('change', settings.pointer, value)
    },
    getCoursesId() {
      axios.get(this.$store.getters.getUrl + 'api/courses')
       .then(function (response) {
          this.courses = response.data.data;
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    getGroupsId() {
        axios.get(this.$store.getters.getUrl + 'api/groups')
         .then(function (response) {
            this.groups = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      }
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getCoursesId();
    this.getGroupsId();

    axios.get(this.$store.getters.getUrl + 'api/activities')
        .then(function (response) {
            this.activities= response.data.data;
            this.$store.commit("setActivities", {
              activities: this.activities
            })
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('login'); 
        });
  }
}
</script>

<style scoped>
.offset_top{
  margin-top: 70px;
}
.datetime{
  display: table;
  margin: 0 auto;
}
.time, .duration{
  display: inline-block;
  border: 1px solid #c4c4ef;
  padding: 7px 8px;
  border-radius: 30px;
  float: left;
  margin: 30px 10px;
  margin-top: 0;
}
.calendars {
  display: grid;
  width: 100%;
  grid-template-columns: repeat(auto-fill, minmax(350px,1fr));
}
.activity{
  margin-top: 30px;
}
.error{
  color: red;
  display: block;
}
.success{
  display: block;
  margin-bottom: 30px;
  color: green;
}
.label{
  display: block;
  margin-top: 15px;
}
.simpleActivity{
  text-align: center;
}
.time span {
  position: relative;
  display: inline-block;
  height: 20px;
  width: 20px;
  cursor: pointer;
  border: none;
  border-radius: 50%;
  text-align: center;
}
input[type=number]{
  border: none;
  border-radius: 30px;
  text-align: center;
}
.nav-button{
  display: inline-block;
  padding-left: 15px;
  padding-right: 15px;
  margin-top: 5px;
}
.navigation_buttons{
  padding: 0;
  margin: 0;
}
.button-primary{
  margin-bottom: 50px;
}
.title, .courseId, .recurrence{
  margin-bottom: 15px;
}
.nav{
  margin: 15px auto;
}
.nav h3{
  border-bottom: 1px solid #c4c4ef;
  width: 45%;
  margin: 0 auto;
  cursor: pointer;
  font-size: 25px;
  padding: 15px 0;
}
.activities_show{
  margin-bottom: 50px;
}
</style>
