import axios from "axios";
import * as utils from "../common/utils/utils";
import exp from "constants";
import {CryptoFormValues} from "../components/CryptoFilters";
import {TRADE_STATUS, paths} from "../common/constants";
import sign from "jwt-encode";
import {LocalStorageMock} from "./LocalStorageMock";
import {UserService} from "../services/UserService";

jest.mock('axios')
Object.defineProperty(window, "localStorage", { value: new LocalStorageMock()});

const test_url = "this/is/a/url"
const code = 1234
const user = {
    username: "salvaCasta",
    password: "castaSalva",
    repeatPassword: "castaSalva",
    phoneNumber: "12345678",
    email: "scastagnino@itba.edu.ar"
}
const secret = 'secret'
const data = {
    sub: user.username,
    name: "Salvador Castagnino",
    role: "Admin"
}
const token = sign(data, secret)


beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({data: []})
    axios.put = jest.fn().mockResolvedValue({data: []})
    axios.patch = jest.fn().mockResolvedValue({data: []})
})

describe("logged in user", () => {
    it("exists", () => {
        const userService = new UserService(() => axios)
        localStorage.setItem("refreshToken", token)

        const loggedInUser = userService.getLoggedInUser()

        expect(loggedInUser).not.toBeNull()
        // @ts-ignore It is already validated that loggedInUser is not null
        expect(loggedInUser.toString()).toMatch(data.sub)
    })
    it("doesnt exist", () => {
        const userService = new UserService(() => axios)
        localStorage.clear()

        const loggedInUser = userService.getLoggedInUser()

        expect(loggedInUser).toBeNull()
    })
})

describe("logged in user's role", () => {
    it("exists", () => {
        const userService = new UserService(() => axios)
        localStorage.setItem("refreshToken", token)

        const loggedInUserRole = userService.getRole()

        expect(loggedInUserRole).not.toBeNull()
        // @ts-ignore It is already validated that loggedInUserRole is not null
        expect(loggedInUserRole.toString()).toMatch(data.role)
    })
    it("doesn't exist", () => {
        const userService = new UserService(() => axios)
        localStorage.clear()

        const loggedInUserRole = userService.getRole()

        expect(loggedInUserRole).toBeNull()
    })
})

test("get user", () => {
    const userService = new UserService(() => axios)

    userService.getUser(user.username)

    expect(axios.get).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.USERS +
        user.username
    )
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get user by url", () => {
    const userService = new UserService(() => axios)

    userService.getUserByUrl(test_url)

    expect(axios.get).toHaveBeenCalledWith(test_url)
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get username frmo uri", () => {
    const userService = new UserService(() => axios)

    const testedUsername = userService.getUsernameFromURI(test_url + user.username)

    expect(testedUsername).toMatch(user.username)
})

test("register new user", () => {
    const userService = new UserService(() => axios)

    userService.register(
        user.username,
        user.password,
        user.repeatPassword,
        user.phoneNumber,
        user.email
    )

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.USERS +
        "register", user
    )
    expect(axios.post).toHaveBeenCalledTimes(1)
})

test("verify user", () => {
    const userService = new UserService(() => axios)

    userService.verifyUser(code, user.username)

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.USERS +
        user.username, {
            code: code
        }
    )
    expect(axios.post).toHaveBeenCalledTimes(1)
})

test("get KYC status", () => {
    const userService = new UserService(() => axios)

    userService.getKYCStatus(user.username)

    expect(axios.get).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.USERS +
        user.username +
        "/kyc"
    )

    expect(axios.get).toHaveBeenCalledTimes(1)
})