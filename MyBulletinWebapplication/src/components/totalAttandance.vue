<template>
   <div class="container">
      <div class="row">
        <section class="col-12">
          <h1>All groups</h1>
            <table class="table table-hover">
                <tr class="header_table">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Attandance Freq[%]</th>
                </tr>
                <tr v-for="attandance in attandances">
                    <td>{{attandance.student.student_id}}</td>
                    <td>{{attandance.student.name}}</td>
                    <td>{{(attandance.total*100).toFixed(2)}}%</td>
                </tr>
            </table>
         </section>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'totalAttandance',
    data() {
      return {
          attandances: [],
          course_id: this.$route.params.id
      }
    },
    methods: {
      getTotalAttandance(){
        axios.get(this.$store.getters.getUrl + 'api/attandancelist/' + this.course_id)
          .then(function (response) {
            this.attandances = response.data;
            console.log(this.attandances);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('login'); 
        });
      }
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
        this.$router.push('/login'); 
      }
      this.getTotalAttandance();
    }
  }
</script>
<style scoped>

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
