<template>
   <div class="container">
        <div class="row">
          <h1>{{courseName}}</h1>
          <div class="col-6">
            <CalendarShow v-if="activities.length != 0" />
          </div>
          <section class="col-6">
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
                  <td>{{activitySelected.activity_id}}</td>
                  <td>{{activitySelected.title}}</td>
                  <td>{{activitySelected.activityDate}}</td>
                  <td>{{activitySelected.hour}}</td>
                  <td>{{activitySelected.duration}}</td>
                  <td><router-link :to="'/activities/' + activitySelected.activity_id">more..</router-link></td>
              </tr>
            </table>
            {{takeActivitiesSelected}}
          </section>
        </div>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" :to="'/course/edit/' + course.id">Edit Course</router-link></li>
              <li><router-link class="nav-button" :to="'/attandancelist/' + course.id">Show attendance list</router-link></li>
          </ul>
       </div>
    </div>
</template>

<script>

import axios from 'axios';
import CalendarShow from './CalendarShow.vue';

export default {
  name: 'course',
  components: {
    CalendarShow
  },
  data () {
    return {
      activities: [],
      activitiesSelected: [],
      courseName: ''
    }
  },
  props: ['course'],
  computed: {
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
  mounted() {
    //this.$refs.calendar.chooseTargetDate("2019/04/14");
    if(this.course){
      this.courseName = this.course.name;
      this.activities = this.course.activities;
      this.$store.commit("setActivities", {
          activities: this.course.activities
      })
    }else{
      let subject_id = localStorage.getItem('subject_id');
      this.$router.push('/courses/'+ subject_id); 
    }
  },  
  methods: {
      
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3,h4{
  text-align: left;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
