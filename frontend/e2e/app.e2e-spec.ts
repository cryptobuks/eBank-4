import { EbankFrontendPage } from './app.po';

describe('ebank-frontend App', () => {
  let page: EbankFrontendPage;

  beforeEach(() => {
    page = new EbankFrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
