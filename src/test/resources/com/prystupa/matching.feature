Feature: Match-point matching logic

  Scenario: 3-way match between two market makers and a market taker
  Bank A and Bank B are “Market Makers.” Bank C, a market taker, initially submits a date
  run with a 3D offer at 3.5. Bank C then improves the offer in the 3D manually, generating
  a 3-way Match
    When market maker "A" quoted the following markets:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  |     |     | 1     | 10  |
      | 2D  | 10  | 2   |       |     |
      | 3D  |     |     |       |     |
    Then RFQ service cannot quote a price for "1Dx3D"
    And no trades are generated

    When market maker "B" quoted the following markets:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  |     |     |       |     |
      | 2D  |     |     | 2     | 10  |
      | 3D  | 10  | 3   |       |     |
    Then RFQ service quotes the following price for "1Dx3D":
      | AMT | Bid | Offer | AMT |
      | 10  | 2   |       |     |
    And no trades are generated

    When market taker "C" submits the following order:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  | 7   | 1   |       |     |
      | 3D  |     |     | 3.5   | 10  |
    Then no trades are generated

    When market taker "C" submits the following order:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  | 7   | 1   |       |     |
      | 3D  |     |     | 3     | 10  |
    Then the following trade suggestions are generated:
      | Day | Rate | Buyer | Seller | Size |
      | 1D  | 1    | C     | A      | 7    |
      | 2D  | 2    | A     | B      | 7    |
      | 3D  | 3    | B     | C      | 7    |