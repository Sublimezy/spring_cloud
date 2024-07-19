


const map = {
  state: {
    lng:'',
    lat:'',
    address:'',
  },
  mutations:{
    SET_LNG: (state, lng) => {
      state.lng = lng
    },
    SET_LAT: (state, lat) => {
      state.lat = lat
    },
    SET_ADDRESS: (state, address) => {
      state.address = address
    }
  },
  actions:{

  }
}
export default map
