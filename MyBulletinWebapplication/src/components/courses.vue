<template>
   <div class="container">
      <div class="row">
        <section class="col-12">
          <h1>Courses</h1>
          <table class="table table-hover">
              <tr class="header_table">
                  <th>Id</th>
                  <th>Name</th>
                  <th>Group</th>
                  <th>Professor</th>
                  <th>Activities</th>
                  <th>Delete</th>
              </tr>
              <tr v-for="(course, index) in courses">
                  <td>{{course.id}}</td>
                  <td>{{course.name}}</td>
                  <td><router-link class="link" :to="'/group/' + course.group[0].group_id">{{course.group[0].name}}</router-link></td>
                  <td>{{course.professor[0].name}}</td>
                  <td>
                   <!-- <select v-model="activity_id" >    
                      <option value="0" disabled selected>Chose activity</option>   
                      <option v-for="activity in course.activities" :value="activity.activity_id">
                        {{activity.activityDate}} {{activity.hour}}
                      </option>                                            
                    </select> -->
                    <router-link :to="{ name: 'course', params: { course: courses[index] } }">more..</router-link>
                  </td>
                  <td><button class="delete" v-on:click=deleteCourse(course.id)>&#x274C;</button></td>
              </tr>
          </table>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" :to="'/courses/add/' + subject_id">Add Course</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'courses',
  data () {
    return {
      courses: [],
      subject_id: this.$route.params.id,
      activity_id: 0
    }
  },
  /*watch: {
    'activity_id': function() {
      this.$router.push('activities/'+ this.activity_id); 
    }
  },*/
  methods: {
      getCourses(){
          axios.get(this.$store.getters.getUrl + 'api/subjects/' + this.subject_id)
        .then(function (response) {
            this.courses = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
        localStorage.setItem('subject_id', this.subject_id);
      },
      deleteCourse(id){
         axios.delete(this.$store.getters.getUrl + 'api/courses/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getCourses();
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
    this.getCourses();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
