import { rest, setupWorker } from 'msw';
import publicRepositoriesResponseJson from './public-repositories-response.json'
import thisIsJhoelResponse from './this-is-jhoel-response.json';

const handlers = [
  rest.get('https://fake-github/users/:username/repos', (req, res, ctx) => {
    return res(
      ctx.json(publicRepositoriesResponseJson),
      ctx.status(200),
      ctx.delay(100)
    )
  }),

  rest.get('https://fake-github/repos/:username/:repository/contents/portfolio-description.json', (req, res, ctx) => {
    return res(
      ctx.json(thisIsJhoelResponse),
      ctx.status(200),
      ctx.delay(2000)
    )
  })
]

export default setupWorker(...handlers);
