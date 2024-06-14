export enum ControllerPaths {
  AUTH = '/auth',
  USER = '/user',
  STREAM = '/stream',
  STUDENT = '/student',
  STDSTATUS = '/stdStatus',
  SPECIALIZATION = '/specialization',
  INTERESTEDIN = '/interestedIn',
  CMNSTATUS = '/cmnStatus',
}

export enum AuthApi {
  AUTHENTICATE = ControllerPaths.AUTH + '/authenticate',
}

export enum UserApis {
  // LOGIN = ControllerPaths.USER + '/login',
  ALL = ControllerPaths.USER + '/all',
  SAVE = ControllerPaths.USER + '/save',
  UPDATE = ControllerPaths.USER,
  DELETE = ControllerPaths.USER,
  FORGOTPASSWORD = ControllerPaths.USER + '/generateotp/',
  VERIFYOTP = ControllerPaths.USER + '/verify/',
  UPDATEPASSWORD = ControllerPaths.USER + '/updatepassword/',
  SEARCHBYNAME=ControllerPaths.USER + '/searchbyname/',
}

export enum StreamApi {
  ALL = ControllerPaths.STREAM + '/all',
  SAVE = ControllerPaths.STREAM + '/save',
  UPDATE = ControllerPaths.STREAM,
  DELETE = ControllerPaths.STREAM,
}

export enum StudentApi {
  ALL = ControllerPaths.STUDENT + '/all',
  ALLDTOS = ControllerPaths.STUDENT + '/alldtos',
  SAVE = ControllerPaths.STUDENT + '/save',
  UPDATE = ControllerPaths.STUDENT,
  DELETE = ControllerPaths.STUDENT,
  REFERENAME = ControllerPaths.STUDENT + '/referName',
  COUNT = ControllerPaths.STUDENT + '/count',
  GETBYSTDNAME = ControllerPaths.STUDENT + '/stdName',
  EMAIL=ControllerPaths.STUDENT+'/email',

  FILTER = ControllerPaths.STUDENT + '/filter',
  FILTERBYSTARTDATE = ControllerPaths.STUDENT + '/filterByStartDate',
  FILTERBYENDDATE = ControllerPaths.STUDENT + '/filterByEndDate',

  GENERATE_EXCEL = ControllerPaths.STUDENT + '/excel',
}

export enum StdStatusApi {
  ALL = ControllerPaths.STDSTATUS + '/all',
  SAVE = ControllerPaths.STDSTATUS + '/save',
  UPDATE = ControllerPaths.STDSTATUS,
  DELETE = ControllerPaths.STDSTATUS,
}
export enum SpecializationApi {
  ALL = ControllerPaths.SPECIALIZATION + '/all',
  SAVE = ControllerPaths.SPECIALIZATION + '/save',
  UPDATE = ControllerPaths.SPECIALIZATION,
  DELETE = ControllerPaths.SPECIALIZATION,
  ALLDTO = ControllerPaths.SPECIALIZATION + '/alldtos',
}
export enum interestedInApi {
  ALL = ControllerPaths.INTERESTEDIN + '/all',
  SAVE = ControllerPaths.INTERESTEDIN + '/save',
  UPDATE = ControllerPaths.INTERESTEDIN,
  DELETE = ControllerPaths.INTERESTEDIN,
}
export enum CmnStatusApi {
  ALL = ControllerPaths.CMNSTATUS + '/all',
  SAVE = ControllerPaths.CMNSTATUS + '/save',
  UPDATE = ControllerPaths.CMNSTATUS,
  DELETE = ControllerPaths.CMNSTATUS,
}
