import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
       token: '',
       name: '',
       serverUrl: 'http://vps669485.ovh.net/',
       activities: [],
       course: '',
       selectedDate: ""
    },
    getters: {
    	getToken(state){
    		return state.token;
    	},
    	getName(state){
    		return state.name;
    	},
        getUrl(state){
            return state.serverUrl;
        },
        getActivities(state){
            return state.activities;
        },
        getSelectedDate(state){
            return state.selectedDate;
        }
    },
    mutations: {
    	setToken(state, payload){
    		state.token = payload.token;
    	},
    	setName(state, payload){
    		state.name = payload.name;
    	},
        setActivities(state, payload){
            state.activities = payload.activities;
        },
        setSelectedDate(state, payload){
            state.selectedDate = payload.selectedDate;
        }
    }
});