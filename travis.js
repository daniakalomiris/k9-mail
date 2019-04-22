const request = require('request')
const async = require('async')

const ENDPOINT = 'https://api.travis-ci.com'
const TOKEN = 'token pKbnW61y6kej08XG_KxDZA'

/*

curl -H "Travis-API-Version: 3" \
     -H "Authorization: token pKbnW61y6kej08XG_KxDZA" \
     https://api.travis-ci.com/repos



     curl -H "Travis-API-Version: 3" \
     -H "Authorization: token pKbnW61y6kej08XG_KxDZA" \
     https://api.travis-ci.com/repo/7566702


     curl -H "Travis-API-Version: 3" \
     -H "Authorization: token pKbnW61y6kej08XG_KxDZA" \
     https://api.travis-ci.com/builds?limit=1



*/

function get(callback) {
    async.waterfall([
        cb => getBuilds(cb),
        (id, cb) => getBuild(id, cb),
        (build, cb) => getLog(build, cb)
    ], (err, build, log) => {
        if (err) return callback(err)

        return callback(null,
            build, log
        )
    })
}

function getBuilds(callback) {
    request.get(`${ENDPOINT}/builds`, {
        headers: { 'Authorization': TOKEN }
    }, (err, res) => {

        if (err) return callback(err)
        try {
            let builds = JSON.parse(res.body)
            return callback(null, builds[0].id)
        } catch (err) {
            return callback(err)
        }
    })
}

function getBuild(buildId, callback) {
    request.get(`${ENDPOINT}/build/${buildId}`, {
        headers: {
            'Authorization': TOKEN,
            "Travis-API-Version": 3
        },
    }, (err, res) => {

        if (err) return callback(err)
        try {

            let build = JSON.parse(res.body)

            if (build && build.file) return callback(build)

            else return callback(null, build)
        } catch (err) {
            console.log(err)
            return callback(err)
        }
    })
}

function getLog(build, callback) {
    request.get(`${ENDPOINT}/job/${build.jobs[0].id}/log`, {
        headers: {
            'Authorization': TOKEN,
            "Travis-API-Version": 3
        }
    }, (err, res) => {

        if (err) return callback(err)
        try {
            let log = JSON.parse(res.body)
            let { id, number, state, duration, event_type, pull_request_title, pull_request_number, started_at, commit, created_by } = build
            let buildInfo = { id, number, state, duration, event_type, pull_request_title, pull_request_number, started_at, commit: { sha: commit.sha, compare_url: commit.compare_url }, created_by: created_by.login }
            let match = log.content.match(/(?<=\>)(.*?)(?=FAILED)/)

            if (match) buildInfo.failed_test = `${match[0]} FAILED... FIX IT !!`

            return callback(null, buildInfo)
        } catch (err) {
            return callback(err)
        }
    })
}

get((err, build) => {
    if (err) console.log("error", err)
    else console.log(build)
})