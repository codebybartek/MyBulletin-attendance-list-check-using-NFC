export default {
    /**
     * @description check the leap year
     * @param {Number} year
     * @return {Boolean}
     */
    isLeapYear(year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0
    },
    getDateObj(date) {
        const dateObj = date ? new Date(date) : new Date()
        return {
            year: dateObj.getFullYear(),
            month: dateObj.getMonth() + 1,
            day: dateObj.getDate(),
            week: dateObj.getDay() 
        }
    },
    /**
    * @description get target date  or today timestamp
    * @param {String} date 
    * @return {Number} 
    */
    getTimestamp(date) {
        return !date ? +new Date() : +new Date(this.formatDate(date, 'YYYY-MM-DD'))
    },
    /**
    * @description conver date by lang
    * @param {Object} year|month|day
    * @return {String} format
    */
    formatDate(date, format) {
        const { year, month, day } = this.getDateObj(date)
        return format.replace(/\[.*?\]|Y{2,4}|M{1,2}|D{1,2}|SSS/g, (match) => {
            return {
                YY: String(year).slice(-2),
                YYYY: year,
                M: month,
                MM: month > 9 ? month : `0${month}`,
                D: day,
                DD: day > 9 ? day : `0${day}`
            }[match]
        })
    },
    /**
    * @description get the total days in the month
    * @param {Number} year
    * @param {Number} month
    * @return {Number} totalDays
    */
    getTotalDays(year, month) {
        return month == 2 ? (this.isLeapYear(year) ? 29 : 28) :
            String(month).match(/\[.*?\]|4|6|9|11|SSS/g) ? 30 : 31
    }
}