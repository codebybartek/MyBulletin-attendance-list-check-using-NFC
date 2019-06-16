<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Edit subject: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="subject.name" type="text" placeholder="Subject name" id="name"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="saveSubject()" >Edit subject</button>
          </div>
       </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'editSubject',
  data () {
    return {
      course_id: this.$route.params.id,
      errors: [],
      success: '',
      subject: {
        name: '',
        subject_id: parseInt(this.$route.params.id),
        _method: "PUT"
      }
    }
  },
  methods: {
    saveSubject() {
        axios.post(this.$store.getters.getUrl + 'api/subjects/' + this.subject.subject_id, this.subject)
        .then((response) =>{
          alert(response.data['updated']);
          getSubject(this.subject.subject_id);
        })
        .catch((error)=>{
         // this.$router.push('login'); 
        });

    },
    getSubject(id) {
      axios.get(this.$store.getters.getUrl + 'api/subjects/' + this.subject.subject_id + '/edit')
       .then(function (response) {
          this.subject.name = response.data[0].name;
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
    }
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getSubject(this.subject.subject_id);
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
input, select{
  width: 100%;
  max-width: 400px;
  display: block;
  margin: 0 auto;
  padding: 10px;
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  text-align: center;
  margin-bottom: 15px;
  outline: none;
}
 #name:focus, #name:active{
  border-color: #00529c;
}
a {
  color: #42b983;
}
</style>
